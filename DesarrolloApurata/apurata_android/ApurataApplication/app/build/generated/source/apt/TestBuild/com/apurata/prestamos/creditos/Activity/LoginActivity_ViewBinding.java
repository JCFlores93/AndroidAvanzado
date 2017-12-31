// Generated code from Butter Knife. Do not modify!
package com.apurata.prestamos.creditos.Activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.apurata.prestamos.creditos.R;
import com.facebook.login.widget.LoginButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.parentView = Utils.findRequiredView(source, R.id.parentView, "field 'parentView'");
    target.imageAlert = Utils.findRequiredViewAsType(source, R.id.imageAlert, "field 'imageAlert'", ImageView.class);
    target.txtAlert = Utils.findRequiredViewAsType(source, R.id.txtAlert, "field 'txtAlert'", TextView.class);
    target.messageLoginView1 = Utils.findRequiredViewAsType(source, R.id.messageLoginView1, "field 'messageLoginView1'", TextView.class);
    target.messageLoginView2 = Utils.findRequiredViewAsType(source, R.id.messageLoginView2, "field 'messageLoginView2'", TextView.class);
    target.messageLoginView3 = Utils.findRequiredViewAsType(source, R.id.messageLoginView3, "field 'messageLoginView3'", TextView.class);
    target.errorMessageNull = Utils.findRequiredViewAsType(source, R.id.txtMessageNull, "field 'errorMessageNull'", TextView.class);
    target.btnGoToApurataCom = Utils.findRequiredViewAsType(source, R.id.btnGoToApurataCom, "field 'btnGoToApurataCom'", Button.class);
    target.progressBarLogin = Utils.findRequiredViewAsType(source, R.id.progressBarLogin, "field 'progressBarLogin'", ProgressBar.class);
    target.txtPrueba = Utils.findRequiredViewAsType(source, R.id.txtPrueba, "field 'txtPrueba'", TextView.class);
    target.loginButton = Utils.findRequiredViewAsType(source, R.id.login_button, "field 'loginButton'", LoginButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.parentView = null;
    target.imageAlert = null;
    target.txtAlert = null;
    target.messageLoginView1 = null;
    target.messageLoginView2 = null;
    target.messageLoginView3 = null;
    target.errorMessageNull = null;
    target.btnGoToApurataCom = null;
    target.progressBarLogin = null;
    target.txtPrueba = null;
    target.loginButton = null;
  }
}
