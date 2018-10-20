#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include "Main.h"
#include <stdio.h>
#include <stdlib.h>
jmethodID add, get;

JNIEXPORT void JNICALL Java_KeyGrabber_listen (JNIEnv *env, jobject obj)
{

	printf("starting to listen to key Ctrl-Shift-1 (native code)\n");
	jclass cls = (*env)->FindClass(env, "Main");

    if(cls == NULL)
    {
        printf("Class Main not found\nExiting...");
        Sleep(500);
        exit(-1);
    }
    
    add = (*env)->GetStaticMethodID(env, cls, "copyData", "(I)V");
    if(add == 0)
    {
        printf("Cannot find method copyData\nExiting...");
        Sleep(500);
        exit(-1);
    }

    get = (*env)->GetStaticMethodID(env, cls, "pasteData", "(I)V");
    if(get == 0)
    {
        printf("Cannot find method getData\nExiting...");
        Sleep(500);
        exit(-1);
    }

    jmethodID mid = (*env)->GetStaticMethodID(env, cls, "fire_key_event", "()V" );
    if(mid ==0){
	   printf("cannot find method fun\n");
	   exit(-1);
    }


    Display*    dpy     = XOpenDisplay(0);

    if(!dpy) 
    {
        printf("Failed to get Display\n");
        exit(EXIT_FAILURE);
    }

    Window      root    = DefaultRootWindow(dpy);
    XEvent      ev;

    unsigned int    modifiers       = ControlMask | ShiftMask;
    int             keycode1        = XKeysymToKeycode(dpy,XK_1);
    int             keycode2        = XKeysymToKeycode(dpy,XK_2);
    int             keycode3        = XKeysymToKeycode(dpy,XK_3);
    int             keycode4        = XKeysymToKeycode(dpy,XK_4);
    int             keycode5        = XKeysymToKeycode(dpy,XK_5);
    int             keycode6        = XKeysymToKeycode(dpy,XK_6);
    int             keycode7        = XKeysymToKeycode(dpy,XK_7);
    int             keycode8        = XKeysymToKeycode(dpy,XK_8);
    Window          grab_window     =  root;
    Bool            owner_events    = False;
    int             pointer_mode    = GrabModeAsync;
    int             keyboard_mode   = GrabModeAsync;

    XGrabKey(dpy, keycode1, modifiers, grab_window, owner_events, pointer_mode,
             keyboard_mode);

    XSelectInput(dpy, root, KeyPressMask );
    for(;;)
    {
    	XNextEvent(dpy, &ev);
    	if(ev.type = KeyPress)
        {
            printf("Pressed\n");    
            //(*env)->CallStaticVoidMethod(env, cls, mid);
        }
    }



    printf("leaving c code\n");

    return;

}
