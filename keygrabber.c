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

void hook()
{
    
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

    hook();
}