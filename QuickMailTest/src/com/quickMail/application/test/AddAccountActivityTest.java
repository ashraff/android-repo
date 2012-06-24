package com.quickMail.application.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.quickMail.application.AddAccountActivity;

public class AddAccountActivityTest extends
		ActivityInstrumentationTestCase2<AddAccountActivity> {
	
	private AddAccountActivity mActivity;  // the activity under test
    private TextView mView;          // the activity's TextView (the only view)
    private String resourceString;


	public AddAccountActivityTest() {
		super("com.quickMail.application", AddAccountActivity.class);

	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        mView = (TextView) mActivity.findViewById(com.quickMail.application.R.id.lbl_email);
        resourceString = mActivity.getString(com.quickMail.application.R.string.lbl_email);
    }
	
	public void testPreconditions() {
	      assertNotNull(mView);
	    }
	    public void testText() {
	      assertEquals(resourceString,(String)mView.getText());
	    }



}
