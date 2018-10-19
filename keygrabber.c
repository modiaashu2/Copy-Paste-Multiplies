#define WINVER 0x0500
#include <windows.h>
#include "Main.h"
#include <stdio.h>
// #include <stdlib.h>
#include <jni.h>
#include <unistd.h>

INPUT ip;
HHOOK hHook = NULL;
KBDLLHOOKSTRUCT kbdstruct;
JNIEnv *genv;
jobject gobj;
jmethodID add, get;


LRESULT CALLBACK hook(int nCode, WPARAM wParam, LPARAM lParam)
{
    if(nCode >= 0)
    {
        if(wParam == WM_KEYDOWN && GetAsyncKeyState(VK_CONTROL) && GetAsyncKeyState(VK_SHIFT))
        {
            kbdstruct = *((KBDLLHOOKSTRUCT *)lParam);
            printf("%x", kbdstruct.vkCode);
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

JNIEXPORT void JNICALL Java_Main_grabkey(JNIEnv *env , jobject obj)
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
    
    add = (*env)->GetStaticMethodID(env, javaclass, "copyData", "()V");
    if(add == 0)
    {
        printf("Cannot find method copyData\nExiting...");
        Sleep(500);
        exit(-1);
    }

    get = (*env)->GetStaticMethodID(env, javaclass, "pasteData", "()V");
    if(get == 0)
    {
        printf("Cannot find method getData\nExiting...");
        Sleep(500);
        exit(-1);
    }

    // Calling the hook

    setuphook();
}
