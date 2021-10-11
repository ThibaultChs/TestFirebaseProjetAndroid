package com.openclassrooms.firebaseoc.ui.planningPoker;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.firebaseoc.databinding.ActivityPlanningPokerBinding;
import com.openclassrooms.firebaseoc.manager.GroupManager;
import com.openclassrooms.firebaseoc.manager.PlanningPokerManager;
import com.openclassrooms.firebaseoc.manager.UserManager;
import com.openclassrooms.firebaseoc.models.Message;
import com.openclassrooms.firebaseoc.models.US;
import com.openclassrooms.firebaseoc.ui.BaseActivity;


public class PlanningPokerActivity extends BaseActivity<ActivityPlanningPokerBinding> {

    private String salon;

    private UserManager userManager = UserManager.getInstance();
    private PlanningPokerManager planningPokerManager = PlanningPokerManager.getInstance();
    private GroupManager groupManager = GroupManager.getInstance();

    @Override
    protected ActivityPlanningPokerBinding getViewBinding() {
        return ActivityPlanningPokerBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                salon= null;
            } else {
                salon= extras.getString("salon");
            }
        } else {
            salon= (String) savedInstanceState.getSerializable("salon");
        }
        setupListeners();
    }

    private void setupListeners(){

        planningPokerManager.getLastUS(salon).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot document : value){
                    if(document.toObject(US.class) != null){
                        US us = document.toObject(US.class);
                        String idUS = document.getId();
                        binding.textUs.setText(us.getEnonce());

                        String username = userManager.getCurrentUser().getDisplayName();
                        binding.button0.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "0");
                        });
                        binding.button05.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "0.5");
                        });
                        binding.button1.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "1");
                        });
                        binding.button2.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "2");
                        });
                        binding.button3.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "3");
                        });
                        binding.button5.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "5");
                        });
                        binding.button8.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "8");
                        });
                        binding.button13.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "13");
                        });
                        binding.button20.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "20");
                        });
                        binding.button40.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "40");
                        });
                        binding.button100.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "100");
                        });
                        binding.buttonInterrogation.setOnClickListener(view -> {
                            planningPokerManager.addNote(username, salon, idUS, "?");
                        });

                        binding.btnUsSuivante.setOnClickListener(view -> {

                        });
                    }
                    else{
                        binding.textUs.setText("En attente d'US du scrum master...");
                    }
                }
            }
        });
        // Send button
        binding.sendButton.setOnClickListener(view -> {
            sendUS();
        });
    }

    private void sendUS(){
        // Check if user can send a message (Text not null + user logged)
        boolean canSendMessage = !TextUtils.isEmpty(binding.chatEditText.getText()) && userManager.isCurrentUserLogged();

        if (canSendMessage){
            // Create a new message for the chat
            planningPokerManager.createUS(binding.chatEditText.getText().toString(), this.salon);
            // Reset text field
            binding.chatEditText.setText("");
        }
    }


}