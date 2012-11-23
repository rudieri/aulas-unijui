package com.aula.carrinho.v2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.*;
import android.media.MediaPlayer;
import android.util.Log;
import com.aula.carrinho.ParametrosActivity;
import com.aula.carrinho.TelaActivity;
import org.anddev.andengine.audio.music.MusicFactory;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class CarrinhoV2View extends CarrinhoV2ViewBase {

    private Mat mgray;
    private Mat mRgba;
    private final TelaActivity tela;
    MediaPlayer mediaPlayer;
    private int ultimaDirValido;
    private int ultimaEsqValido;
    int potencia = 0;
    int ignorarLinhas = 0;
    private Bitmap mBitmap;
    private int previewWidtd;
    private int previewHeight;
    int[] mRGBA;
    private int[] rgba;

    public CarrinhoV2View(Context context) {
        super(context);

        this.tela = (TelaActivity) context;
        MusicFactory.setAssetBasePath("mfx/");
        mediaPlayer = new MediaPlayer() {
        };
        try {
            mediaPlayer.reset();
            mediaPlayer.setLooping(true);
            AssetFileDescriptor assetFileDescritor = context.getAssets().openFd("mfx/acelera.mp3");
            mediaPlayer.setDataSource(assetFileDescritor.getFileDescriptor(), assetFileDescritor.getStartOffset(), assetFileDescritor.getLength());
            mediaPlayer.prepare();
//            mediaPlayer.start();
        } catch (Exception ex) {
            Log.e(TAG, "Erro ao Statea Musica", ex);
        }

        potencia = ParametrosActivity.potencia;
        ignorarLinhas = ParametrosActivity.ignorarLinhas;
    }

    @Override
    protected void onPreviewStarted(int previewWidtd, int previewHeight) {
        // initialize Mats before usage
        mgray = new Mat(getFrameHeight() + getFrameHeight() / 2, getFrameWidth(), CvType.CV_8UC1);
        mRGBA = new int[previewWidtd * previewHeight];
        mRgba = new Mat();


        mBitmap = Bitmap.createBitmap(previewWidtd, previewHeight, Bitmap.Config.ARGB_8888);
        this.previewWidtd = previewWidtd;
        this.previewHeight = previewHeight;
    }

    @Override
    protected void onPreviewStopped() {

        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }

        // Explicitly deallocate Mats
        if (mgray != null) {
            mgray.release();
        }
        if (mRgba != null) {
            mRgba.release();
        }

        mgray = null;
        mRgba = null;

    }

    protected Bitmap processFrame(byte[] data) {
        


        int frameSize = getFrameWidth() * getFrameHeight();

        rgba = mRGBA;

        for (int i = 0; i < getFrameHeight(); i++) {
            for (int j = 0; j < getFrameWidth(); j++) {
                int index = i * getFrameWidth() + j;
                int supply_index = frameSize + (i >> 1) * getFrameWidth() + (j & ~1);
                int y = (0xff & ((int) data[index]));
                int u = (0xff & ((int) data[supply_index + 0]));
                int v = (0xff & ((int) data[supply_index + 1]));
                y = y < 16 ? 16 : y;

                float y_conv = 1.164f * (y - 16);
                int r = Math.round(y_conv + 1.596f * (v - 128));
                int g = Math.round(y_conv - 0.813f * (v - 128) - 0.391f * (u - 128));
                int b = Math.round(y_conv + 2.018f * (u - 128));

                r = r < 0 ? 0 : (r > 255 ? 255 : r);
                g = g < 0 ? 0 : (g > 255 ? 255 : g);
                b = b < 0 ? 0 : (b > 255 ? 255 : b);

                rgba[i * getFrameWidth() + j] = 0xff000000 + (b << 16) + (g << 8) + r;
            }
        }
        
            mgray.put(0, 0, data);
            Imgproc.resize(mgray, mgray, new Size(getFrameWidth(), getFrameHeight()));

//        Imgproc.cvtColor(mgray, mRgba, Imgproc.COLOR_YUV420sp2RGB, 0);


            //Monocromatico
//        Imgproc.threshold(mgray, mgray, 100, 255d, Imgproc.THRESH_BINARY);



            ///Processa
            ////
        /*
             * Reconhecedor MATRIZ
             */

            //Numero de Divisoes Matrix
            int maxCols = 9;
            int maxRow = 9;
            int auxC = 0;//getRedFrameWidth()/maxCols;
            int auxR = 0;//getRedFrameHeight()/maxRow;
            int potenciaEsq = 80;
            int potenciaDir = 80;

            int meio = (maxCols / 2);
            String view = "";
            int[][] pontos = new int[maxCols][maxRow];
            Mat[][] matrix = new Mat[maxCols][maxRow];
            //
            int columSel = -1;
            int rowSel = -1;
            //

            for (int r = maxRow - 1; r != 0; r--) {
                for (int c = 0; c < maxCols; c++) {

                    if (r < ignorarLinhas) {
                        continue;
                    }
                    if (((r == 2 || r == 3 || r == 4 || r == 5) && (c == 3 || c == 4 || c == 5))
                            || ((r == 6 || r == 7) && c == 5)) {//Ignorar Meios em forma de cone
//                    Log.v(TAG + "Ignorou", c + "x" + r);
                        continue;
                    }
                    //Quebra a Imagem em Subimagens
                    auxC = (c * (mgray.rows() / maxCols));
                    auxR = (r * (mgray.cols() / maxRow));
                    int fimC = (c * (mgray.rows() / maxCols)) + (mgray.rows() / maxCols);
                    int fimR = (r * (mgray.cols() / maxRow)) + (mgray.cols() / maxRow);
//                Log.i(TAG, "Submat " + "[" + c + "][" + r + "] " + auxR + "," + fimR + "," + auxC + "," + fimC);
                    Mat submat = mgray.submat(auxC, fimC, auxR, fimR);
                    matrix[c][r] = submat;
                    // FIm 

                    //Varre a Subimagem para ver se é branco ou preto
                    Bitmap aux = Bitmap.createBitmap(submat.cols(), submat.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(submat, aux);
                    int totalPreto = 0;
                    int totalBranco = 0;
                    for (int i = 0; i < submat.cols(); i += 10) {//amostragem de 10 e 10
                        for (int j = 0; j < submat.rows(); j += 10) {
                            int pixel = aux.getPixel(i, j);
                            if (pixel == Color.BLACK) {
                                totalPreto++;
                            } else {
                                totalBranco++;
                            }
                        }
                    }
                    if (totalPreto > totalBranco) {
                        pontos[c][r] = Color.BLACK;
                    } else {
                        pontos[c][r] = Color.WHITE;
                    }
                    //FIM


//                Log.i(TAG, "Coluna: " + c + " Branco: " + totalBranco + " Preto: " + totalPreto);

                    if (pontos[c][r] == Color.BLACK && columSel < Math.abs(c - meio)) {
                        columSel = c;
                        rowSel = r;
                    }
                }



            }

            /**
             * CODE OLD if (potenciaDir < 0) { potenciaDir = 0; } if
             * (potenciaEsq < 0) { potenciaEsq = 0; } if (potenciaDir > 100) {
             * potenciaDir = 99; } if (potenciaEsq > 100) { potenciaEsq = 99; }
             */
//        Log.v(TAG, columSel + "x" + rowSel);
            switch (columSel) {
                case 0://Bem no canto                 
                    potenciaDir = potencia * 1;
                    potenciaEsq = (int) (potencia * -1);
                    break;
                case 1:
                    potenciaDir = potencia * 1;
                    potenciaEsq = (int) (potencia * -0.7);
                    break;
                case 2:
                    potenciaDir = potencia * 1;
                    potenciaEsq = (int) (potencia * 0.1);
                    break;
                case 3:// um pouco pra esquerda ajuste
                    potenciaDir = (int) (potencia * 1);
                    potenciaEsq = (int) (potencia * 0.8);
                    break;
                case 4: // no meio Potencia Maxima a frente
                    potenciaDir = (int) (potencia * 1);
                    potenciaEsq = (int) (potencia * 1);
                    break;
                case 5:
                    potenciaEsq = potencia * 1;
                    potenciaDir = (int) (potencia * 0.8);
                    break;
                case 6:
                    potenciaEsq = (int) (potencia * 1);
                    potenciaDir = (int) (potencia * 0.1);
                    break;
                case 7:
                    potenciaEsq = potencia * 1;
                    potenciaDir = (int) (potencia * -0.70);
                    break;
                case 8:
                    potenciaEsq = potencia * 1;
                    potenciaDir = potencia * -1;
                    break;
                default://Fudeu Engata RE
                    potenciaEsq = (int) (potencia * -0.50);
                    potenciaDir = (int) (potencia * -0.50);
                    break;

            }
            if (potenciaEsq == (int) (potencia * -0.50) && potenciaDir == (int) (potencia * -0.50)) {
                /*
                 * se for igual, não muda nada se esquerda é maior, estava andando
                 * para a direita, logo, a ré deve tender para a direita a mesma
                 * coisa (só que ao contrário) para a direita maior
                 */
                if (ultimaEsqValido > ultimaDirValido) {
                    potenciaEsq = (int) (potencia * -0.2);
                } else {
                    potenciaDir = (int) (potencia * -0.2);
                }
            } else {
                ultimaEsqValido = potenciaEsq;
                ultimaDirValido = potenciaDir;
            }

//        if (potenciaDir == (int)(potencia*1) && potenciaEsq == (int)(potencia*1) ) {
//            tela.enviarCamera(90);
//        } else {
//            tela.enviarCamera(0);
//        }


//        Log.v(TAG + " Potencia", potenciaEsq + "x" + potenciaDir + "   \n" + view);
            tela.enviarPotencia(potenciaEsq, potenciaDir);

            if (ParametrosActivity.preview) {

                Bitmap bmp = mBitmap;
                try {
                    if (ParametrosActivity.rgb) {
//                        Imgproc.resize(mRgba, mRgba, new Size(mBitmap.getWidth(), mBitmap.getHeight()));
//                        Utils.matToBitmap(mRgba, bmp);
                        bmp.setPixels(rgba, 0/* offset */, getFrameWidth() /* stride */, 0, 0, getFrameWidth(), getFrameHeight());
                    } else {
                        Imgproc.resize(mgray, mgray, new Size(mBitmap.getWidth(), mBitmap.getHeight()));
                        Utils.matToBitmap(mgray, bmp);
                    }
                } catch (Exception ex) {
                }
                return bmp;
            } else {
                return null;
            }

        }



    
}