// Generated code from Butter Knife. Do not modify!
package com.apurata.prestamos.creditos.Activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.apurata.prestamos.creditos.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserMenuActivity_ViewBinding implements Unbinder {
  private UserMenuActivity target;

  @UiThread
  public UserMenuActivity_ViewBinding(UserMenuActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserMenuActivity_ViewBinding(UserMenuActivity target, View source) {
    this.target = target;

    target.btnNewApplication = Utils.findRequiredViewAsType(source, R.id.btnNewApplication, "field 'btnNewApplication'", Button.class);
    target.btnContinueApplication = Utils.findRequiredViewAsType(source, R.id.btnContinueApplication, "field 'btnContinueApplication'", Button.class);
    target.btnContactUs = Utils.findRequiredViewAsType(source, R.id.btnContactUs, "field 'btnContactUs'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserMenuActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnNewApplication = null;
    target.btnContinueApplication = null;
    target.btnContactUs = null;
  }
}
