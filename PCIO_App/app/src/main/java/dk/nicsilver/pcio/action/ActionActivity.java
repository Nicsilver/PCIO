package dk.nicsilver.pcio.action;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nicsilver.pcio.R;

import dk.nicsilver.pcio.KeyboardHelper;
import dk.nicsilver.pcio.select.SelectActivity;
import timber.log.Timber;

public class ActionActivity extends AppCompatActivity implements ActionContract.view, View.OnClickListener
{
    private ActionContract.presenter presenter;
    private ImageButton keyboardButton, mediaButton;
    private EditText keyboardEditText;
    private TextView textView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activity);
        
        Intent intent = getIntent();
        String port = intent.getStringExtra(SelectActivity.PORT_MESSAGE);
        String ip = intent.getStringExtra(SelectActivity.IP_MESSAGE);
        presenter = new ActionPresenter(this, ip, port);
        
        this.textView = findViewById(R.id.keyboard_text_view);
        this.textView.setPaintFlags(0);
        
        this.keyboardButton = findViewById(R.id.keyboard_button);
        this.mediaButton = findViewById(R.id.media_button);
        this.keyboardEditText = findViewById(R.id.keyboard_edit_text);
        keyboardEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        
        this.keyboardButton.setOnClickListener(this);
        this.keyboardEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }
            
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
                if (before < count)
                {
                    Timber.d(String.valueOf(charSequence.charAt(start + before)));
                }
                textView.setText(charSequence);
            }
            
            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
    }
    
    @Override
    public void onClick(View view)
    {
        // R.id values are no longer compile-time constants under AGP 8, so a switch won't compile here.
        if (view.getId() == R.id.keyboard_button)
        {
            if (!this.keyboardEditText.hasFocus())
            {
                this.keyboardEditText.requestFocus();
                KeyboardHelper.showKeyboard(this);
            } else
            {
                KeyboardHelper.hideKeyboard(this);
                this.keyboardEditText.clearFocus();
            }
        }
    }
    
    @Override
    public void onConnectionLost()
    {
        
    }
    
    @Override
    public void onConnectionRegained()
    {
        
    }
}
