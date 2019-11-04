package com.example.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private  static CrimeLab sCrimelab;

    private List<Crime> mCriems;

    public static CrimeLab get(Context context){
        if (sCrimelab == null){
            sCrimelab=new CrimeLab(context);
        }
        return sCrimelab;
    }

    private CrimeLab(Context context) {
        mCriems=new ArrayList<>();
        for (int i =0; i<100; i++){
            Crime crime = new Crime();
            crime.setmTitle("Crime#"+i);
            crime.setmSolved(i%2==0);
            mCriems.add(crime);
        }
    }

    public List<Crime> getCrimes(){
        return mCriems;
    }

    public Crime getCrime(UUID id) {
        for(Crime crime:mCriems){
            if(crime.getmId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
