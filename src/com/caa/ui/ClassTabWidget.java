package com.caa.ui;

import com.caa.bspace.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class ClassTabWidget extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classtab);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Resusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, GradeViewActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("grades").setIndicator("Gradebook", res.getDrawable(R.layout.gradebooktab)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, LinksViewActivity.class);

        spec = tabHost.newTabSpec("links").setIndicator("Links", res.getDrawable(R.layout.linkstab)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, SyllabusViewActivity.class);

        spec = tabHost.newTabSpec("syllabus").setIndicator("Syllabus", res.getDrawable(R.layout.syllabustab)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, FilesViewActivity.class);

        spec = tabHost.newTabSpec("files").setIndicator("Files", res.getDrawable(R.layout.filestab)).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
