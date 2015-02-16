package my.ijat.megacachecleaner;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Clean extends Activity {
	
	int[] status = {0,0};
	TextView tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_clean);
		
		tv1 = (TextView) findViewById(R.id.textView1);
		tv1.setText(tv1.getText() + "App start! \n");
		if (deleteExtFiles("Android/data/nz.mega.android/cache/previewsMEGA")) status[0] = 1;
		if (deleteExtFiles("Android/data/nz.mega.android/cache/thumbnailsMEGA")) status[1] = 1;
		if (status[0] == 1 & status[1] == 1) {
			final Toast tos = Toast.makeText(getApplicationContext(), "Mega cache cleaned!",Toast.LENGTH_SHORT);
			Thread one = new Thread() {
				public void run() {
					try {
						sleep(5000);
						tos.show();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					finish();
				}
			};
			one.start();
		}		
	}
	
	private boolean deleteExtFiles (String folder) {
		Toast tos0;
		
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Log.i("action","Media mount - OK");
			if (!Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				Log.i("action","Media read - OK");

				File path = Environment.getExternalStorageDirectory();
				if (path.canWrite()) {
					Log.i("action","Can Write - OK");

					File a = new File(path.toString() + "/" + folder);
					tv1.setText(tv1.getText() + "\nAccessing \""+ folder +"\" folder.\n");
					if (a.exists()) {
						tv1.setText(tv1.getText() + "Folder exists, proceed cleaning process...\n");
						Log.i("action","Folder exists - OK");
						String[] lists = a.list();
						if (lists.length > 0) {
							Log.i("action","Files exits - OK");
							File thefile = null;
							for (int i = 0; i < lists.length; i++) {
								thefile = new File(a.toString() + '/'
										+ lists[i]);
								thefile.delete();
								Log.i("action",thefile.getAbsolutePath().toString() + " - Deleted");
							}
							tv1.setText(tv1.getText() + "Folder cleaned. Total "+ lists.length +" files deleted.\n");
							//tos0 = Toast.makeText(getApplicationContext(), a.getAbsolutePath().toString() + " - Cleaned!",Toast.LENGTH_SHORT);
							//tos0.show();
							return true;
						} else {
							tv1.setText(tv1.getText() + "Folder is empty.\n");
							Log.i("action","Folder already empty");
							//tos0 = Toast.makeText(getApplicationContext(), a.getAbsolutePath().toString() + " - Folder already empty.",Toast.LENGTH_SHORT);
							//tos0.show();
							return true;
						}
							
					} else {
						tv1.setText(tv1.getText() + "Folder not exists!\n");
						Log.i("action","Folder not exists.");
						//tos0 = Toast.makeText(getApplicationContext(), a.getAbsolutePath().toString() + " - Folder not exitst!",Toast.LENGTH_SHORT);
						//tos0.show();
						return false;
					}

					// Write text file to external storage
					// File file = new File(path, "test.txt");
					/*
					 * if (!file.exists()) { Toast tos3 =
					 * Toast.makeText(getApplicationContext(), "Path: " +
					 * file.getAbsolutePath(), Toast.LENGTH_SHORT); tos3.show();
					 * 
					 * FileWriter thisfile = null; try { thisfile = new
					 * FileWriter(file,true); thisfile.write("Hello World!");
					 * thisfile.close(); } catch (IOException e1) { // TODO
					 * Auto-generated catch block e1.printStackTrace(); } }
					 */
				}
			}

		}
		else {
			tv1.setText("External storage access failed.\n");
			Log.e("action","failed");
			tos0 = Toast.makeText(getApplicationContext(), "Failed!",Toast.LENGTH_SHORT);
			tos0.show();
			return false;
		}
		return false;
	}
	
}
