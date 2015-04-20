package ca.easyevent.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Participant;
import ca.easyevent.utils.ImageManager;


public class ParticipantActivity extends Activity {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private Participant participant;

    private TextView nameText, telText, mailText;
    private static final int EDIT_PARTICIPANT = 101;

    private DAOParticipant participantDAO;


    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity);

        participantDAO = new DAOParticipant(this);

        long idParticipant = getIntent().getLongExtra("PARTICIPANT", 0);

        participantDAO.open();
        participant = participantDAO.getParticipant(idParticipant);
        participantDAO.close();

        nameText = (TextView)this.findViewById(R.id.name_part_text);
        telText = (TextView)this.findViewById(R.id.tel_part_text);
        mailText = (TextView)this.findViewById(R.id.mail_part_text);

        nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
        telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
        mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);

            //Button edition
        final View editFlottingButton = findViewById(R.id.edit_button);
        editFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipantActivity.this, ParticipantFormActivity.class);
                intent.putExtra("PARTICIPANT", participant.getId());
                startActivity(intent);
            }
        });

    }

    protected  void onResume(){
        super.onResume();
        participantDAO.open();
        long idParticipant = getIntent().getLongExtra("PARTICIPANT", 0);
        participant = participantDAO.getParticipant(idParticipant);

        nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
        telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
        mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);
        initImage();

        participantDAO.close();
    }

    public void initImage() {
        ImageView imageView = (ImageView)findViewById(R.id.upload_image_preview);
        if (participant.getImage() != null && !participant.getImage().equals("default")) {
            Display display = getWindowManager().getDefaultDisplay();
            double deviceWidth = display.getWidth();

            ImageManager imageManager = new ImageManager(this, deviceWidth);
            BitmapFactory.Options options = new BitmapFactory.Options();

            Bitmap bitmap = BitmapFactory.decodeFile(participant.getImage(), options);
            imageView.setImageDrawable(imageManager.getResizeImage(bitmap, options));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setVisibility(View.VISIBLE);
        } else
            imageView.setVisibility(View.GONE);
    }

}
