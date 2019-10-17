package com.example.xpto.raxapp.cronometro;



import android.os.CountDownTimer;


import java.util.Calendar;

public class Cronometro {

    private CountDownTimer cdt;
    private long tempoIntervalo = 100;
    private long tempoDuracao;
    private long tempoRestante;
    private Calendar cld;
    private String tempo;
    private boolean pausado;
    private boolean zerado;
    private CronometroInteracao ci;



    public Cronometro(long tempoDuracao, CronometroInteracao ci){
        this.tempoDuracao = tempoDuracao;
        this.tempoRestante = tempoDuracao;
        this.init();
        this.ci = ci;
        this.cld = Calendar.getInstance();
        tempo = formatarTempo(this.tempoRestante);
    }


    public void init() {
        if (cdt != null){ cdt.cancel(); }
        if (tempoRestante == 0){ tempoRestante = tempoDuracao; }
        cdt = new CountDownTimer(tempoRestante, tempoIntervalo) {
            @Override
            public void onTick(long l) {
                if (pausado || zerado){
                    cancel();
                } else {
                    tempoRestante = l;
                    tempo = formatarTempo(tempoRestante);
                    ci.tempoRolando(tempo);
                }
            }
            @Override
            public void onFinish() {
                tempoRestante = 0;
                tempo = formatarTempo(tempoRestante);
                ci.tempoFinalizado(tempo);
            }
        };
    }

    public void iniciar() {
        pausado = false;
        zerado = false;
        init();
        cdt.start();
    }

    public void pausar() {
        pausado = true;
    }

    public void zerar() {
        zerado = true;
        tempoRestante = tempoDuracao;
        tempo = formatarTempo(tempoRestante);
        ci.tempoRolando(tempo);
    }

    public String getTempo() {
        return tempo;
    }

    private String formatarTempo(long millisUntilFinished){
        cld.setTimeInMillis(millisUntilFinished);
        String minuto = cld.get(Calendar.MINUTE) < 10 ? "0"+ cld.get(Calendar.MINUTE) : ""+ cld.get(Calendar.MINUTE);
        String segundo = cld.get(Calendar.SECOND) < 10 ? "0"+ cld.get(Calendar.SECOND) : ""+ cld.get(Calendar.SECOND);
        String decimo = (cld.get(Calendar.MILLISECOND)/100) < 10 ? "0"+(cld.get(Calendar.MILLISECOND)/100) : ""+(cld.get(Calendar.MILLISECOND)/100);
        return minuto+":"+segundo+":"+decimo;
    }


}


