package com.gianlu.dnshero.Domain;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.gianlu.commonutils.CommonUtils;
import com.gianlu.commonutils.SuperTextView;
import com.gianlu.dnshero.GlueView;
import com.gianlu.dnshero.NetIO.Domain;
import com.gianlu.dnshero.R;
import com.gianlu.dnshero.Utils;

public class RootNameserverFragment extends Fragment {
    public static RootNameserverFragment getInstance(Context context, Domain domain) {
        RootNameserverFragment fragment = new RootNameserverFragment();
        Bundle args = new Bundle();
        args.putString("title", context.getString(R.string.rootNameServer));
        args.putSerializable("root", domain.root);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.fragment_root_nameserver, container, false);

        Bundle args = getArguments();
        Domain.RootNameserver root;
        if (getContext() == null || args == null || (root = (Domain.RootNameserver) args.getSerializable("root")) == null)
            return null;

        SuperTextView name = layout.findViewById(R.id.rootNsFragment_name);
        name.setTypeface("fonts/Roboto-Medium.ttf");
        name.setText(root.name);

        SuperTextView rtt = layout.findViewById(R.id.rootNsFragment_rtt);
        rtt.setHtml(R.string.rttBlack, Utils.formatRTT(root.rtt));

        final LinearLayout nameservers = layout.findViewById(R.id.rootNsFragment_nameservers);
        for (String nameserver : root.nameservers) {
            SuperTextView textView = new SuperTextView(getContext(), nameserver);
            textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.secondary_text_light));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            nameservers.addView(textView);
        }

        final ImageButton toggleNs = layout.findViewById(R.id.rootNsFragment_toggleNs);
        toggleNs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.handleCollapseClick(toggleNs, nameservers);
            }
        });

        GlueView glue = layout.findViewById(R.id.rootNsFragment_glue);
        glue.setGlue(root.glue);

        return layout;
    }
}
