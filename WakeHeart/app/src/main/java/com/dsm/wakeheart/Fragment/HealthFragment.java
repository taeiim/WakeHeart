package com.dsm.wakeheart.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsm.wakeheart.Activity.SettingsActivity;
import com.dsm.wakeheart.R;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class HealthFragment extends android.support.v4.app.Fragment {

    View rootView;
    ImageView manImage;
    ImageView womanImage;
    RelativeLayout imgLayout;
    ImageView settingsBtn;
    int gender;
    int age;

    int bpm;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_health, container, false);

        setTable();
//        setBPM();

        //설정 버튼 누르면 설정 액티비티로 넘어감
        settingsBtn = (ImageView) rootView.findViewById(R.id.setting_icon);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void setTable() {
        final RelativeLayout table_man = (RelativeLayout) rootView.findViewById(R.id.table_man);
        final RelativeLayout table_woman = (RelativeLayout) rootView.findViewById(R.id.table_woman);
        table_woman.setVisibility(View.GONE);

        // 남자 표 누르면 남자표 사라지고 여자표 보이기
        table_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_man.setVisibility(View.GONE);
                table_woman.setVisibility(View.VISIBLE);
            }
        });

        // 여자 표 누르면 여자표 사라지고 남자표 보이기
        table_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_man.setVisibility(View.VISIBLE);
                table_woman.setVisibility(View.GONE);
            }
        });


        gender = 0;
        age = 48;
        bpm = 78;

        // 남자일때
        if (gender == 0) {
            TextView athlete = (TextView) rootView.findViewById(R.id.man_health_athlete);
            TextView verygood = (TextView) rootView.findViewById(R.id.man_health_verygood);
            TextView good = (TextView) rootView.findViewById(R.id.man_health_good);
            TextView aboveAvg = (TextView) rootView.findViewById(R.id.man_health_aboveAvg);
            TextView avg = (TextView) rootView.findViewById(R.id.man_health_avg);
            TextView belowAvg = (TextView) rootView.findViewById(R.id.man_health_belowAvg);
            TextView bad = (TextView) rootView.findViewById(R.id.man_health_bad);

            if (age >= 18 && age <= 25) {  // 18-25살 일때
                TextView man_age_18 = (TextView) rootView.findViewById(R.id.man_age_18);
                man_age_18.setBackgroundColor(Color.YELLOW);
                //bpm에 맞춰서 배경색
                if (bpm >= 49 && bpm <= 55) {
                    TextView man_1_18 = (TextView) rootView.findViewById(R.id.man_1_18);
                    man_1_18.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 56 && bpm <= 61) {
                    TextView man2_18 = (TextView) rootView.findViewById(R.id.man_2_18);
                    man2_18.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 65) {
                    TextView man3_18 = (TextView) rootView.findViewById(R.id.man_3_18);
                    man3_18.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 66 && bpm <= 69) {
                    TextView man4_18 = (TextView) rootView.findViewById(R.id.man_4_18);
                    man4_18.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 70 && bpm <= 73) {
                    TextView man5_18 = (TextView) rootView.findViewById(R.id.man_5_18);
                    man5_18.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 74 && bpm <= 81) {
                    TextView man6_18 = (TextView) rootView.findViewById(R.id.man_6_18);
                    man6_18.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 82) {
                    TextView man7_18 = (TextView) rootView.findViewById(R.id.man_7_18);
                    man7_18.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age >= 26 && age <= 35) {   // 26-35살
                TextView man_age_26 = (TextView) rootView.findViewById(R.id.man_age_26);
                man_age_26.setBackgroundColor(Color.YELLOW);
                if (bpm >= 49 && bpm <= 54) {
                    TextView man_1_26 = (TextView) rootView.findViewById(R.id.man_1_26);
                    man_1_26.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 55 && bpm <= 61) {
                    TextView man2_26 = (TextView) rootView.findViewById(R.id.man_2_26);
                    man2_26.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 65) {
                    TextView man3_26 = (TextView) rootView.findViewById(R.id.man_3_26);
                    man3_26.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 66 && bpm <= 70) {
                    TextView man4_26 = (TextView) rootView.findViewById(R.id.man_4_26);
                    man4_26.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 71 && bpm <= 74) {
                    TextView man5_26 = (TextView) rootView.findViewById(R.id.man_5_26);
                    man5_26.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 75 && bpm <= 81) {
                    TextView man6_26 = (TextView) rootView.findViewById(R.id.man_6_26);
                    man6_26.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 82) {
                    TextView man7_26 = (TextView) rootView.findViewById(R.id.man_7_26);
                    man7_26.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age >= 36 && age <= 45) {
                TextView man_age_36 = (TextView) rootView.findViewById(R.id.man_age_36);
                man_age_36.setBackgroundColor(Color.YELLOW);
                if (bpm >= 50 && bpm <= 56) {
                    TextView man_1_36 = (TextView) rootView.findViewById(R.id.man_1_36);
                    man_1_36.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 57 && bpm <= 62) {
                    TextView man2_36 = (TextView) rootView.findViewById(R.id.man_2_36);
                    man2_36.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 63 && bpm <= 66) {
                    TextView man3_36 = (TextView) rootView.findViewById(R.id.man_3_36);
                    man3_36.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 67 && bpm <= 70) {
                    TextView man4_36 = (TextView) rootView.findViewById(R.id.man_4_36);
                    man4_36.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 71 && bpm <= 75) {
                    TextView man5_36 = (TextView) rootView.findViewById(R.id.man_5_36);
                    man5_36.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 76 && bpm <= 82) {
                    TextView man6_36 = (TextView) rootView.findViewById(R.id.man_6_36);
                    man6_36.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 83) {
                    TextView man7_36 = (TextView) rootView.findViewById(R.id.man_7_36);
                    man7_36.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age >= 46 && age <= 55) {
                TextView man_age_46 = (TextView) rootView.findViewById(R.id.man_age_46);
                man_age_46.setBackgroundColor(Color.YELLOW);
                if (bpm >= 50 && bpm <= 57) {
                    TextView man_1_46 = (TextView) rootView.findViewById(R.id.man_1_46);
                    man_1_46.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 58 && bpm <= 63) {
                    TextView man2_46 = (TextView) rootView.findViewById(R.id.man_2_46);
                    man2_46.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 64 && bpm <= 67) {
                    TextView man3_46 = (TextView) rootView.findViewById(R.id.man_3_46);
                    man3_46.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 68 && bpm <= 71) {
                    TextView man4_46 = (TextView) rootView.findViewById(R.id.man_4_46);
                    man4_46.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 72 && bpm <= 76) {
                    TextView man5_46 = (TextView) rootView.findViewById(R.id.man_5_46);
                    man5_46.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 77 && bpm <= 83) {
                    TextView man6_46 = (TextView) rootView.findViewById(R.id.man_6_46);
                    man6_46.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 84) {
                    TextView man7_46 = (TextView) rootView.findViewById(R.id.man_7_46);
                    man7_46.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }


            } else if (age >= 56 && age <= 65) {
                TextView man_age_56 = (TextView) rootView.findViewById(R.id.man_age_56);
                man_age_56.setBackgroundColor(Color.YELLOW);
                if (bpm >= 51 && bpm <= 56) {
                    TextView man_1_56 = (TextView) rootView.findViewById(R.id.man_1_56);
                    man_1_56.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 57 && bpm <= 61) {
                    TextView man2_56 = (TextView) rootView.findViewById(R.id.man_2_56);
                    man2_56.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 67) {
                    TextView man3_56 = (TextView) rootView.findViewById(R.id.man_3_56);
                    man3_56.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 68 && bpm <= 71) {
                    TextView man4_56 = (TextView) rootView.findViewById(R.id.man_4_56);
                    man4_56.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 72 && bpm <= 75) {
                    TextView man5_56 = (TextView) rootView.findViewById(R.id.man_5_56);
                    man5_56.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 76 && bpm <= 81) {
                    TextView man6_56 = (TextView) rootView.findViewById(R.id.man_6_56);
                    man6_56.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 82) {
                    TextView man7_56 = (TextView) rootView.findViewById(R.id.man_7_56);
                    man7_56.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age > 65) {
                TextView man_age_65 = (TextView) rootView.findViewById(R.id.man_age_65);
                man_age_65.setBackgroundColor(Color.YELLOW);
                if (bpm >= 50 && bpm <= 55) {
                    TextView man_1_65 = (TextView) rootView.findViewById(R.id.man_1_65);
                    man_1_65.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 56 && bpm <= 61) {
                    TextView man2_65 = (TextView) rootView.findViewById(R.id.man_2_65);
                    man2_65.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 65) {
                    TextView man3_65 = (TextView) rootView.findViewById(R.id.man_3_65);
                    man3_65.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 66 && bpm <= 69) {
                    TextView man4_65 = (TextView) rootView.findViewById(R.id.man_4_65);
                    man4_65.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 70 && bpm <= 73) {
                    TextView man5_65 = (TextView) rootView.findViewById(R.id.man_5_65);
                    man5_65.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 74 && bpm <= 79) {
                    TextView man6_65 = (TextView) rootView.findViewById(R.id.man_6_65);
                    man6_65.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 80) {
                    TextView man7_36 = (TextView) rootView.findViewById(R.id.man_7_65);
                    man7_36.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            }

        } else if (gender == 1) {
            TextView athlete = (TextView) rootView.findViewById(R.id.woman_health_athlete);
            TextView verygood = (TextView) rootView.findViewById(R.id.woman_health_verygood);
            TextView good = (TextView) rootView.findViewById(R.id.woman_health_good);
            TextView aboveAvg = (TextView) rootView.findViewById(R.id.woman_health_aboveAvg);
            TextView avg = (TextView) rootView.findViewById(R.id.woman_health_avg);
            TextView belowAvg = (TextView) rootView.findViewById(R.id.woman_health_belowAvg);
            TextView bad = (TextView) rootView.findViewById(R.id.woman_health_bad);

            if (age >= 18 && age <= 25) {  // 18-25살 일때
                TextView woman_age_18 = (TextView) rootView.findViewById(R.id.woman_age_18);
                woman_age_18.setBackgroundColor(Color.YELLOW);
                //bpm에 맞춰서 배경색
                if (bpm >= 49 && bpm <= 55) {
                    TextView woman_1_18 = (TextView) rootView.findViewById(R.id.woman_1_18);
                    woman_1_18.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 56 && bpm <= 61) {
                    TextView woman2_18 = (TextView) rootView.findViewById(R.id.woman_2_18);
                    woman2_18.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 65) {
                    TextView woman3_18 = (TextView) rootView.findViewById(R.id.woman_3_18);
                    woman3_18.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 66 && bpm <= 69) {
                    TextView woman4_18 = (TextView) rootView.findViewById(R.id.woman_4_18);
                    woman4_18.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 70 && bpm <= 73) {
                    TextView woman5_18 = (TextView) rootView.findViewById(R.id.woman_5_18);
                    woman5_18.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 74 && bpm <= 81) {
                    TextView woman6_18 = (TextView) rootView.findViewById(R.id.woman_6_18);
                    woman6_18.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 82) {
                    TextView woman7_18 = (TextView) rootView.findViewById(R.id.woman_7_18);
                    woman7_18.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age >= 26 && age <= 35) {   // 26-35살
                TextView woman_age_26 = (TextView) rootView.findViewById(R.id.woman_age_26);
                woman_age_26.setBackgroundColor(Color.YELLOW);
                if (bpm >= 49 && bpm <= 54) {
                    TextView woman_1_26 = (TextView) rootView.findViewById(R.id.woman_1_26);
                    woman_1_26.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 55 && bpm <= 61) {
                    TextView woman2_26 = (TextView) rootView.findViewById(R.id.woman_2_26);
                    woman2_26.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 65) {
                    TextView woman3_26 = (TextView) rootView.findViewById(R.id.woman_3_26);
                    woman3_26.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 66 && bpm <= 70) {
                    TextView woman4_26 = (TextView) rootView.findViewById(R.id.woman_4_26);
                    woman4_26.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 71 && bpm <= 74) {
                    TextView woman5_26 = (TextView) rootView.findViewById(R.id.woman_5_26);
                    woman5_26.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 75 && bpm <= 81) {
                    TextView woman6_26 = (TextView) rootView.findViewById(R.id.woman_6_26);
                    woman6_26.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 82) {
                    TextView woman7_26 = (TextView) rootView.findViewById(R.id.woman_7_26);
                    woman7_26.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age >= 36 && age <= 45) {
                TextView woman_age_36 = (TextView) rootView.findViewById(R.id.woman_age_36);
                woman_age_36.setBackgroundColor(Color.YELLOW);
                if (bpm >= 50 && bpm <= 56) {
                    TextView woman_1_36 = (TextView) rootView.findViewById(R.id.woman_1_36);
                    woman_1_36.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 57 && bpm <= 62) {
                    TextView woman2_36 = (TextView) rootView.findViewById(R.id.woman_2_36);
                    woman2_36.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 63 && bpm <= 66) {
                    TextView woman3_36 = (TextView) rootView.findViewById(R.id.woman_3_36);
                    woman3_36.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 67 && bpm <= 70) {
                    TextView woman4_36 = (TextView) rootView.findViewById(R.id.woman_4_36);
                    woman4_36.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 71 && bpm <= 75) {
                    TextView woman5_36 = (TextView) rootView.findViewById(R.id.woman_5_36);
                    woman5_36.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 76 && bpm <= 82) {
                    TextView woman6_36 = (TextView) rootView.findViewById(R.id.woman_6_36);
                    woman6_36.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 83) {
                    TextView woman7_36 = (TextView) rootView.findViewById(R.id.woman_7_36);
                    woman7_36.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age >= 46 && age <= 55) {
                TextView woman_age_46 = (TextView) rootView.findViewById(R.id.woman_age_46);
                woman_age_46.setBackgroundColor(Color.YELLOW);
                if (bpm >= 50 && bpm <= 57) {
                    TextView woman_1_46 = (TextView) rootView.findViewById(R.id.woman_1_46);
                    woman_1_46.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 58 && bpm <= 63) {
                    TextView woman2_46 = (TextView) rootView.findViewById(R.id.woman_2_46);
                    woman2_46.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 64 && bpm <= 67) {
                    TextView woman3_46 = (TextView) rootView.findViewById(R.id.woman_3_46);
                    woman3_46.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 68 && bpm <= 71) {
                    TextView woman4_46 = (TextView) rootView.findViewById(R.id.woman_4_46);
                    woman4_46.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 72 && bpm <= 76) {
                    TextView woman5_46 = (TextView) rootView.findViewById(R.id.woman_5_46);
                    woman5_46.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 77 && bpm <= 83) {
                    TextView woman6_46 = (TextView) rootView.findViewById(R.id.woman_6_46);
                    woman6_46.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 84) {
                    TextView woman7_46 = (TextView) rootView.findViewById(R.id.woman_7_46);
                    woman7_46.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }


            } else if (age >= 56 && age <= 65) {
                TextView woman_age_56 = (TextView) rootView.findViewById(R.id.woman_age_56);
                woman_age_56.setBackgroundColor(Color.YELLOW);
                if (bpm >= 51 && bpm <= 56) {
                    TextView woman_1_56 = (TextView) rootView.findViewById(R.id.woman_1_56);
                    woman_1_56.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 57 && bpm <= 61) {
                    TextView woman2_56 = (TextView) rootView.findViewById(R.id.woman_2_56);
                    woman2_56.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 67) {
                    TextView woman3_56 = (TextView) rootView.findViewById(R.id.woman_3_56);
                    woman3_56.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 68 && bpm <= 71) {
                    TextView woman4_56 = (TextView) rootView.findViewById(R.id.woman_4_56);
                    woman4_56.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 72 && bpm <= 75) {
                    TextView woman5_56 = (TextView) rootView.findViewById(R.id.woman_5_56);
                    woman5_56.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 76 && bpm <= 81) {
                    TextView woman6_56 = (TextView) rootView.findViewById(R.id.woman_6_56);
                    woman6_56.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 82) {
                    TextView woman7_56 = (TextView) rootView.findViewById(R.id.woman_7_56);
                    woman7_56.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            } else if (age > 65) {
                TextView woman_age_65 = (TextView) rootView.findViewById(R.id.woman_age_65);
                woman_age_65.setBackgroundColor(Color.YELLOW);
                if (bpm >= 50 && bpm <= 55) {
                    TextView woman_1_65 = (TextView) rootView.findViewById(R.id.woman_1_65);
                    woman_1_65.setBackgroundColor(Color.YELLOW);
                    athlete.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 56 && bpm <= 61) {
                    TextView woman2_65 = (TextView) rootView.findViewById(R.id.woman_2_65);
                    woman2_65.setBackgroundColor(Color.YELLOW);
                    verygood.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 62 && bpm <= 65) {
                    TextView woman3_65 = (TextView) rootView.findViewById(R.id.woman_3_65);
                    woman3_65.setBackgroundColor(Color.YELLOW);
                    good.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 66 && bpm <= 69) {
                    TextView woman4_65 = (TextView) rootView.findViewById(R.id.woman_4_65);
                    woman4_65.setBackgroundColor(Color.YELLOW);
                    aboveAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 70 && bpm <= 73) {
                    TextView woman5_65 = (TextView) rootView.findViewById(R.id.woman_5_65);
                    woman5_65.setBackgroundColor(Color.YELLOW);
                    avg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 74 && bpm <= 79) {
                    TextView woman6_65 = (TextView) rootView.findViewById(R.id.woman_6_65);
                    woman6_65.setBackgroundColor(Color.YELLOW);
                    belowAvg.setBackgroundColor(Color.YELLOW);
                } else if (bpm >= 80) {
                    TextView woman7_36 = (TextView) rootView.findViewById(R.id.woman_7_65);
                    woman7_36.setBackgroundColor(Color.YELLOW);
                    bad.setBackgroundColor(Color.YELLOW);
                }

            }

        }
    }

//    private void setBPM(){
//        bpm = 50;
//
//    }

}
