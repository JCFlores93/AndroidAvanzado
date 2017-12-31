// Generated code from Butter Knife. Do not modify!
package com.apurata.prestamos.creditos.Activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.apurata.prestamos.creditos.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReturnToWebActivity_ViewBinding implements Unbinder {
  private ReturnToWebActivity target;

  @UiThread
  public ReturnToWebActivity_ViewBinding(ReturnToWebActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReturnToWebActivity_ViewBinding(ReturnToWebActivity target, View source) {
    this.target = target;

    target.btnGoToWebPage = Utils.findRequiredViewAsType(source, R.id.btnGoToWebPage, "field 'btnGoToWebPage'", Button.class);
    target.txtMainText = Utils.findRequiredViewAsType(source, R.id.txtMainText, "field 'txtMainText'", TextView.class);
    target.btnCall = Utils.findRequiredViewAsType(source, R.id.btnCall, "field 'btnCall'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReturnToWebActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnGoToWebPage = null;
    target.txtMainText = null;
    target.btnCall = null;
  }
}
