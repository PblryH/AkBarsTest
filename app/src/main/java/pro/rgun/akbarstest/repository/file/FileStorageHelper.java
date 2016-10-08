package pro.rgun.akbarstest.repository.file;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import timber.log.Timber;

/**
 * Created by rgun on 08.10.16.
 */

public class FileStorageHelper {

    public static final String STORAGE_FILE = "storage";

    private Context mContext;

    public FileStorageHelper(Context context) {
        mContext = context;
    }

    public String readFromFile(){
        String text = "";
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(mContext.openFileInput(STORAGE_FILE)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line + "\n");
            }
            text = buffer.toString();
        } catch (FileNotFoundException e) {
            Timber.e(e, "readFromFile");
        } catch (IOException e) {
            Timber.e(e, "readFromFile");
        }
        Timber.d(text);
        return text;
    }

    public void writeToFile(String content, boolean createFileIfNotExists){
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(STORAGE_FILE, mContext.MODE_WORLD_WRITEABLE);
        } catch (FileNotFoundException e) {
            if(createFileIfNotExists) {
                createFile();
                writeToFile(content, false);
            }
            return;
        }
        try {
            // Create buffered writer
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            Timber.e(e, "writeToFile");
        }
    }

    private void createFile(){
        File storageFile = new File(STORAGE_FILE);
        try {
            storageFile.createNewFile();
        } catch (IOException e) {
            Timber.e(e,"onStorageFileCreate");
        }
    }
}
