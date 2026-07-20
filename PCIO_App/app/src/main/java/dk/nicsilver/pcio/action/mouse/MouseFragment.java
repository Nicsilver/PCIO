package dk.nicsilver.pcio.action.mouse;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nicsilver.pcio.R;

public class MouseFragment extends Fragment implements MouseContract.view, View.OnTouchListener
{
    private MouseContract.presenter presenter;
    private ImageView mouseView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mouse_fragment, parent, false);
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
        mouseView = view.findViewById(R.id.mouse_view);
        mouseView.setOnTouchListener(this);
    }
    //    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//
//
//        return inflater.inflate(R.layout.mouse_fragment, container, false);
//    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.presenter = new MousePresenter(this);
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case (MotionEvent.ACTION_DOWN):
                presenter.onDownLeft(event.getX(), event.getY());
                break;
            
            case (MotionEvent.ACTION_POINTER_DOWN):
                if (event.getActionIndex() == 1)
                {
                    presenter.onDownRight(event.getX(), event.getY());
                }
                break;
            
            case (MotionEvent.ACTION_MOVE):
                if (event.getPointerCount() == 1)
                {
                    presenter.onMoveLeft(event.getX(), event.getY());
                } else if (event.getPointerCount() == 2)
                {
                    presenter.onMoveRight(event.getY());
                }
                break;
            
            case (MotionEvent.ACTION_UP):
                presenter.onUpLeft(event.getX(), event.getY());
                break;
        }
        return true;
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        presenter = null;
        this.mouseView = null;
    }
}
