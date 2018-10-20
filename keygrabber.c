#define WINVER 0x0500
#include <windows.h>
#include "Main.h"
#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include <unistd.h>

INPUT ip;
HHOOK hHook = NULL;
KBDLLHOOKSTRUCT kbdstruct;
JNIEnv *genv;
jobject gobj;
jmethodID add, get;

void Copy(int x)
{ 
    ip.ki.wVk = x;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));
    Sleep(35);
    ip.ki.wVk = VK_SHIFT;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));
    ip.ki.wVk = VK_CONTROL;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;    
    SendInput(1, &ip, sizeof(INPUT));

    Sleep(20);
    ip.ki.wVk = VK_CONTROL;
    ip.ki.dwFlags = 0; 
    SendInput(1, &ip, sizeof(INPUT));

    ip.ki.wVk = 'C';
    ip.ki.dwFlags = 0; 
    SendInput(1, &ip, sizeof(INPUT));

    ip.ki.wVk = 'C';
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));

    ip.ki.wVk = VK_CONTROL;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));

    printf("COPIED\n");

}

void Paste(int x)
{
    ip.ki.wVk = x;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));
    Sleep(35);
    ip.ki.wVk = VK_SHIFT;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));
    ip.ki.wVk = VK_CONTROL;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;    
    SendInput(1, &ip, sizeof(INPUT));

    Sleep(20);
    ip.ki.wVk = VK_CONTROL;
    ip.ki.dwFlags = 0; 
    SendInput(1, &ip, sizeof(INPUT));

    ip.ki.wVk = 'V';
    ip.ki.dwFlags = 0; 
    SendInput(1, &ip, sizeof(INPUT));

    ip.ki.wVk = 'V';
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));

    ip.ki.wVk = VK_CONTROL;
    ip.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &ip, sizeof(INPUT));

    printf("PASTED\n");
}

LRESULT CALLBACK hook(int nCode, WPARAM wParam, LPARAM lParam)
{
    if(nCode >= 0)
    {
        if(wParam == WM_KEYDOWN && GetAsyncKeyState(VK_CONTROL) && GetAsyncKeyState(VK_SHIFT))
        {
            kbdstruct = *((KBDLLHOOKSTRUCT *)lParam);
            int x = kbdstruct.vkCode;
            if(x == 0x31 || x == 0x33 || x == 0x35 || x == 0x37)
            {
                (*genv)->CallStaticVoidMethod(genv, gobj, add, (x - 0x31)/2);
                
            }
            else if(x == 0x32 || x == 0x34 || x == 0x36 || x == 0x38)
            {
                (*genv)->CallStaticVoidMethod(genv, gobj, get, (x - 0x32)/2);
                Sleep(30);
                Paste(x);
            }
        }
    }
}

void setuphook()
{
    ip.type = INPUT_KEYBOARD;
    ip.ki.wScan = 0;
    ip.ki.time = 0;
    ip.ki.dwExtraInfo = 0;
    MSG msg;
	hHook = SetWindowsHookEx(WH_KEYBOARD_LL, hook , NULL, (int)NULL);
    printf("Hook setup successfully!");
    while(!GetMessage(&msg, NULL, (int)NULL, (int)NULL)) 
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
	
    UnhookWindowsHookEx(hHook);
}

JNIEXPORT void JNICALL _Java_Main_grabkey(JNIEnv *env , jobject obj)
{
    // Declaring the java class and methods
    genv = env;
    gobj = obj;
    jclass javaclass = (*env)->FindClass(env, "Main");
    if(javaclass == NULL)
    {
        printf("Class Main not found\nExiting...");
        Sleep(500);
        exit(-1);
    }
    
    add = (*env)->GetStaticMethodID(env, javaclass, "copyData", "(I)V");
    if(add == 0)
    {
        printf("Cannot find method copyData\nExiting...");
        Sleep(500);
        exit(-1);
    }

    get = (*env)->GetStaticMethodID(env, javaclass, "pasteData", "(I)V");
    if(get == 0)
    {
        printf("Cannot find method getData\nExiting...");
        Sleep(500);
        exit(-1);
    }

    // Calling the hook

    setuphook();
}
