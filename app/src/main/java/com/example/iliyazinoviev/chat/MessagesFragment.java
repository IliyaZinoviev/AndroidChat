package com.example.iliyazinoviev.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessagesFragment extends ListFragment {

    private ArrayList<Item> items = new ArrayList<Item>();
    private ChatListAdapter mAdapter;
    private Socket mSocket;
    private String mUsername;
    private String mEmail;
    private static final int REQUEST_LOGIN = 0;
    private ImageButton btnPushMes;

    public MessagesFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new ChatListAdapter(getActivity(), R.layout.item, items);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnPushMes = getActivity().findViewById(R.id.pushMessage);
        btnPushMes.setOnClickListener(heandlerBtnPushMes);
        mSocket = ((ChatApplication) getActivity().getApplication()).getSocket();
        mSocket.on("message", onSendMes);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private View.OnClickListener heandlerBtnPushMes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = ((EditText) getActivity().findViewById(R.id.inputText)).getText().toString();
            mSocket.emit("message", text);
            //mAdapter.add(new Item("1", text, text));
            //setListAdapter(mAdapter);
        }
    };

    private final Emitter.Listener onSendMes = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject res = (JSONObject) args[0];
                        items.add(new Item(res.getString("id"), res.getString("user"), res.getString("message")));
                       // mAdapter.notifyItemInserted(items.size() - 1);
                       // scrollToBottom();
                        setListAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
//
//    private void scrollToBottom() {
//        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
//    }
}

