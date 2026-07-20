package dk.nicsilver.pcio.select;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import dk.nicsilver.pcio.action.ActionActivity;
import dk.nicsilver.pcio.select.model.IIpAndName;

import com.nicsilver.pcio.R;

import timber.log.Timber;

import java.util.List;

public class SelectActivity extends AppCompatActivity implements SelectContract.View
{
    private SelectContract.Presenter presenter;
    private ListView ipList;
    private List<IIpAndName> ips;
    private TextView searchingText;
    
    public static final String PORT_MESSAGE = "com.example.SelectActivity.PORT";
    public static final String IP_MESSAGE = "com.example.SelectActivity.IP";

//    TextView ipText;
//    Button connectButton;
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            presenter.startBroadcast();
        }
    }
    
    @Override
    protected void onPause()
    {
        Timber.i("");
        super.onPause();
    }
    
    @Override
    protected void onResume()
    {
        Timber.i("");
        super.onResume();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("New create");
        
        setContentView(R.layout.select_activity);
        searchingText = findViewById(R.id.searching_text);
        searchingText.setAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        ipList = findViewById(R.id.ip_list);
        ipList.setOnItemClickListener((parent, view, position, id) ->
        {
            Timber.d("onItemClick: pos clicked - %s", position);
            presenter.connect(ips.get(position).getIp());
        });
        presenter = new SelectPresenter(this);
        presenter.startBroadcast();
    }
    
    @Override
    public void setupIpList(final List<IIpAndName> ips)
    {
        this.ips = ips;
        runOnUiThread(() ->
        {
            Timber.d("");
            IpAddressListAdapter adapter = new IpAddressListAdapter(SelectActivity.this, R.layout.ip_list_adapter_view, SelectActivity.this.ips);
            ipList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (ips.size() <= 0)
            {
                if (searchingText.getVisibility() != View.VISIBLE)
                {
                    // visible
                    searchingText.setVisibility(View.VISIBLE);
                    searchingText.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
                }
            } else
            {
                searchingText.setVisibility(View.GONE);
                searchingText.clearAnimation();
            }
        });
    }
    
    @Override
    public void successFullConnect(String port, String receivedIp)
    {
        Timber.i("port: " + port + " ip: " + receivedIp);
        Intent intent = new Intent(this, ActionActivity.class);
        intent.putExtra(PORT_MESSAGE, port);
        intent.putExtra(IP_MESSAGE, receivedIp);
        startActivity(intent);
    }
}