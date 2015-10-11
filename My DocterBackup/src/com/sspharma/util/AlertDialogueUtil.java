package com.sspharma.util;

import com.sspharma.RegistrationActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class AlertDialogueUtil {
	public static AlertDialog.Builder showAlertDialogueWithPositiveButton(Context context,String message,String Title){
		AlertDialog.Builder dialogue=new AlertDialog.Builder(context);
		dialogue.setTitle(Title);
		dialogue.setMessage(message);
		return dialogue;
		
	}
	public static AlertDialog showAlertDialogueWithNegativeButton(Context context,String message,String Title){
		return null;
		
	}
}
