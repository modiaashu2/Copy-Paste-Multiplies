#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <jni.h>
#include "Main.h"


INPUT input;
HHOOK hHook = NULL;
KBDLLHOOKSTRUCT kbdstruct;
JNIEnv genv;
jobject gobj;
jmethodID add, get;


LRESULT CALLBACK hook(int nCode, WPARAM wParam, LPARAM lParam)
{
    if(nCode >= 0)
    {
        if(wParam == WM_KEYDOWN && GetAsyncKeyState(VK_CONTROL) && GetAsyncKeyState(VK_SHIFT))
        {
            kbdstruct = *((KBDLLHOOKSTRUCT *)lParam);
            printf("%x", kbdstruct.vkcode);
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
	hHock = SetWindowsHookEx(WH_KEYBOARD_LL, MyLowLevelHook , NULL,NULL);
    printf("Hook setup successfully!");
    while(!GetMessage(&msg, NULL, NULL, NULL)) {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
	
    UnhookWindowsHookEx(hHock);
}

JNIEXPORT void JNICALL Java_Main_grabkey(JNIEnv *env , jobject obj)
{

    // Declaring the java class and methods

    jclass javaclass = (*env)->FindClass(env, "Main");
    if(jclass == NULL)
    {
        printf("Class Main not found\nExiting...");
        sleep(500);
        exit(-1);
    }
    
    add = (*env)->GetStaticMethodID(env, javaclass, "copyData", "()V");
    if(add == 0)
    {
        printf("Cannot find method copyData\nExiting...");
        sleep(500);
        exit(-1);
    }

    get = (*env)->GetStaticMethodID(env, javaclass, "pasteData", "()V");
    if(get == 0)
    {
        printf("Cannot find method getData\nExiting...");
        sleep(500);
        exit(-1);
    }

    // Calling the hook

    setuphook();
}