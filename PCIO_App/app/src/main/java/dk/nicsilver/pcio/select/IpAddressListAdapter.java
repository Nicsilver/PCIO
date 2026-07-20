package dk.nicsilver.pcio.select;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dk.nicsilver.pcio.select.model.IIpAndName;
import com.nicsilver.pcio.R;

import java.util.List;
import java.util.Objects;

public class IpAddressListAdapter extends ArrayAdapter<IIpAndName>
{
    private Context context;
    private int resource;

    public IpAddressListAdapter(@NonNull Context context, int resource, @NonNull List<IIpAndName> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String ip = Objects.requireNonNull(getItem(position)).getIp();
        
        LayoutInflater inflater = LayoutInflater.from(context);
        
        if (convertView == null)
        {
            convertView = inflater.inflate(resource, parent, false);
        }
        
        TextView textIp = convertView.findViewById(R.id.ip);
        TextView textName = convertView.findViewById(R.id.name);
        
        textIp.setText(ip);
        textName.setText(name);
        
        return convertView;
    }
}
