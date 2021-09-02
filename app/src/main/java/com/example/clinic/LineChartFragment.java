package com.example.clinic;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import ViewModels.DataChartViewModel;
import pojo.DataChart;

public class LineChartFragment extends Fragment {

    LineChart mChart1;
    LineChart mChart2;
    LineChart mChart3;


    ArrayList<Entry> GlucoseValues = new ArrayList<>();
    ArrayList<Entry> BloodValuesLow = new ArrayList<>();
    ArrayList<Entry> BloodValuesHigh = new ArrayList<>();
    ArrayList<Entry> oxygenationValues = new ArrayList<>();

    DataChartViewModel dataChartViewModel = DataChartViewModel.getINSTANCE();
    int role;
    int patient_id;

    private void getData(){

    role = ((MainActivity)getActivity()).getRoleID();

    switch (role){

        case 3:
            patient_id = ((MainActivity)getActivity()).getPatient_ID();
            break;
        case 2:
            patient_id = ((MainActivity)getActivity()).getCurrentPatientProfileID();
            break;
    }


        System.out.println("patientID : " + patient_id + " role : " + role + " in datachart");

    dataChartViewModel.getData(patient_id,getActivity());


    }


    private void findViews(){

        mChart1 = getView().findViewById(R.id.chart);
        mChart2 = getView().findViewById(R.id.chart2);
        mChart3 = getView().findViewById(R.id.chart3);

    }

    private void observeData(){

        dataChartViewModel.getAppointmentListMutableLiveData().observe(getActivity(), new Observer<List<DataChart>>() {
            @Override
            public void onChanged(List<DataChart> dataCharts) {
                for(DataChart dataChart:dataCharts){
                    String[] spdate = dataChart.getSession_date().split("-");
                    System.out.println("dayy : " + Float.parseFloat(spdate[2]));
                    GlucoseValues.add(new Entry(Float.parseFloat(spdate[2]),dataChart.getBlood_glocuse()));
                    oxygenationValues.add(new Entry(Float.parseFloat(spdate[2]),dataChart.getOxygenation()));
                    BloodValuesHigh.add(new Entry(Float.parseFloat(spdate[2]),dataChart.getBlood_pressure_high()));
                    BloodValuesLow.add(new Entry(Float.parseFloat(spdate[2]),dataChart.getBlood_pressure_low()));
                }

                drawLineChartGlucose();
                drawLineChartBlood();
                drawLineChartOxygenation();
            }
        });

    }

    private void drawLineChartGlucose(){

        LineDataSet set1;
        if (mChart1.getData() != null &&
                mChart1.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart1.getData().getDataSetByIndex(0);
            set1.setValues(GlucoseValues);
            mChart1.getData().notifyDataChanged();
            mChart1.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(GlucoseValues, "Glucose");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart1.setData(data);
        }


    }

    private void drawLineChartBlood(){

        LineDataSet set2,set3;
        LineData data = new LineData();
        if (mChart2.getData() != null &&
                mChart2.getData().getDataSetCount() > 0) {
            set2 = (LineDataSet) mChart2.getData().getDataSetByIndex(0);
            set2.setValues(BloodValuesLow);
            mChart2.getData().notifyDataChanged();
            mChart2.notifyDataSetChanged();
        } else {
            set2 = new LineDataSet(BloodValuesLow, "Low Pressure");
            set2.setDrawIcons(false);
            set2.enableDashedLine(10f, 5f, 0f);
            set2.enableDashedHighlightLine(10f, 5f, 0f);
            set2.setColor(Color.DKGRAY);
            set2.setCircleColor(Color.DKGRAY);
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setDrawCircleHole(false);
            set2.setValueTextSize(9f);
            set2.setDrawFilled(true);
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                set2.setFillDrawable(drawable);
            } else {
                set2.setFillColor(Color.DKGRAY);
            }
            data.addDataSet(set2);
        }



        if (mChart2.getData() != null &&
                mChart2.getData().getDataSetCount() > 0) {
            set3 = (LineDataSet) mChart2.getData().getDataSetByIndex(0);
            set3.setValues(BloodValuesHigh);
            mChart2.getData().notifyDataChanged();
            mChart2.notifyDataSetChanged();
        } else {
            set3 = new LineDataSet(BloodValuesHigh, "High Pressure");
            set3.setDrawIcons(false);
            set3.enableDashedLine(10f, 5f, 0f);
            set3.enableDashedHighlightLine(10f, 5f, 0f);
            set3.setColor(Color.RED);
            set3.setCircleColor(Color.RED);
            set3.setLineWidth(1f);
            set3.setCircleRadius(3f);
            set3.setDrawCircleHole(false);
            set3.setValueTextSize(9f);
            set3.setDrawFilled(true);
            set3.setFormLineWidth(1f);
            set3.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set3.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                set2.setFillDrawable(drawable);
            } else {
                set2.setFillColor(Color.RED);
            }
            data.addDataSet(set3);
            mChart2.setData(data);
        }



    }

    private void drawLineChartOxygenation(){


        LineDataSet set1;
        if (mChart3.getData() != null &&
                mChart3.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart3.getData().getDataSetByIndex(0);
            set1.setValues(oxygenationValues);
            mChart3.getData().notifyDataChanged();
            mChart3.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(oxygenationValues, "Oxygenation");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart3.setData(data);
        }



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_linechart_time, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        getData();
        observeData();
        drawLineChartGlucose();
        drawLineChartBlood();
        drawLineChartOxygenation();


    }

}
