package com.xiangxm.contact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.checkpackage.R;
import com.xiangxm.cls.User;


public class AddNew extends Activity implements ViewFactory {
	
	EditText et_name;
	EditText et_mobilePhone;
	EditText et_officePhone;
	EditText et_familyPhone;
	EditText et_position;
	EditText et_company;
	EditText et_address;
	EditText et_zipCode;
	EditText et_otherContact;
	EditText et_email;
	EditText et_remark;
	Button btn_save;
	Button btn_return;
	
	int privacy;//�����ж���ӵ��û��ǲ��Ǳ��ܵ�
	ImageButton imageButton;//ͷ��ť
	View imageChooseView;//ͼ��ѡ�����ͼ
	AlertDialog imageChooseDialog;//ͷ��ѡ��Ի���
	Gallery gallery;//ͷ���Gallery
	ImageSwitcher is;//ͷ���ImageSwitcher
	int currentImagePosition;//���ڼ�¼��ǰѡ��ͼ����ͼ�������е�λ��
	int previousImagePosition;//���ڼ�¼��һ��ͼƬ��λ��
	boolean imageChanged;//�ж�ͷ����û�б仯
	/**
	 * ���е�ͼ��ͼƬ
	 */
	private  int[] images 
			= new int[]{R.drawable.icon
		,R.drawable.image1,R.drawable.image2,R.drawable.image3
		,R.drawable.image4,R.drawable.image5,R.drawable.image6
		,R.drawable.image7,R.drawable.image8,R.drawable.image9
		,R.drawable.image10,R.drawable.image11,R.drawable.image12
		,R.drawable.image13,R.drawable.image14,R.drawable.image15
		,R.drawable.image16,R.drawable.image17,R.drawable.image18
		,R.drawable.image19,R.drawable.image20,R.drawable.image21
		,R.drawable.image22,R.drawable.image23,R.drawable.image24
		,R.drawable.image25,R.drawable.image26,R.drawable.image27
		,R.drawable.image28,R.drawable.image29,R.drawable.image30};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnew);
		Intent intent = getIntent();
		//��ȷ���ñ�������
		if(intent.getExtras()!=null && intent.getExtras().getInt("privacy") == 1) {
			privacy = 1;
		} else {
			privacy = 0;
		}
		
		
		et_name = (EditText)findViewById(R.id.username);
		et_mobilePhone = (EditText)findViewById(R.id.mobilephone);
		et_officePhone = (EditText)findViewById(R.id.officephone);
		et_familyPhone = (EditText)findViewById(R.id.familyphone);
		et_position = (EditText)findViewById(R.id.position);
		et_company = (EditText)findViewById(R.id.company);
		et_address = (EditText)findViewById(R.id.address);
		et_zipCode = (EditText)findViewById(R.id.zipcode);
		et_otherContact = (EditText)findViewById(R.id.othercontact);
		et_email = (EditText)findViewById(R.id.email);
		et_remark = (EditText)findViewById(R.id.remark);
		
		btn_save = (Button)findViewById(R.id.save);
		btn_return = (Button)findViewById(R.id.btn_return);
		imageButton = (ImageButton)findViewById(R.id.image_button); 
		
		/**
		 * ��Ӧ����¼�
		 */
		btn_save.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				//�ж������Ƿ�Ϊ��
				String name = et_name.getText().toString();
				if(name.trim().equals("")) {
					Toast.makeText(AddNew.this, "��������Ϊ��", Toast.LENGTH_LONG).show();
					return;
				}
				//�ӱ��ϻ�ȡ����
				User user = new User();
				user.username = name;
				user.address = et_address.getText().toString();
				user.company = et_company.getText().toString();
				user.email = et_email.getText().toString();
				user.familyPhone = et_familyPhone.getText().toString();
				user.mobilePhone = et_mobilePhone.getText().toString();
				user.officePhone = et_officePhone.getText().toString();
				user.otherContact = et_otherContact.getText().toString();
				user.position = et_position.getText().toString();
				user.remark = et_remark.getText().toString();
				user.zipCode = et_zipCode.getText().toString();
				//�ж�ͷ���Ƿ�ı䣬���ı䣬���õ�ǰ��λ�ã���û�иı䣬����ǰһ�ص�λ��
				if(imageChanged) {
					user.imageId = images[currentImagePosition%images.length];
				} else {
					user.imageId = images[previousImagePosition%images.length];
				}
				user.privacy = privacy;
				//�������ݿ������
				DBHelper helper = new DBHelper(AddNew.this);
				//�����ݿ�
				helper.openDatabase();
				//��user�洢�����ݿ���
				long result = helper.insert(user);
				
				//ͨ��������ж��Ƿ����ɹ�����Ϊ1�����ʾ��������ʧ��
				if(result == -1 ) {
					Toast.makeText(AddNew.this, "���ʧ��", Toast.LENGTH_LONG);
				}
				setTitle("�û���ӳɹ�!");
				//���ص���һ��Activity��Ҳ����Main.activity
				setResult(3);
				//���ٵ�ǰ��ͼ
				finish();
			}
			
		});
		
		btn_return.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();
			}
		});
		
		imageButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
				loadImage();//Ϊgalleryװ��ͼƬ
				initImageChooseDialog();//��ʼ��imageChooseDialog
				imageChooseDialog.show();
			}
		});
		
	}
	
	public void loadImage() {
		if(imageChooseView == null) {
			LayoutInflater li = LayoutInflater.from(AddNew.this);
			imageChooseView = li.inflate(R.layout.imageswitch, null);
			
			//ͨ����Ⱦxml�ļ����õ�һ����ͼ��View�������õ����View�����Gallery
			gallery = (Gallery)imageChooseView.findViewById(R.id.gallery);
			//ΪGalleryװ��ͼƬ
			gallery.setAdapter(new ImageAdapter(this));
			gallery.setSelection(images.length/2);
			is = (ImageSwitcher)imageChooseView.findViewById(R.id.imageswitch);
			is.setFactory(this);
			is.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
	        //ж��ͼƬ�Ķ���Ч��
	        is.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
			gallery.setOnItemSelectedListener(new OnItemSelectedListener(){

				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					//��ǰ��ͷ��λ��Ϊѡ�е�λ��
					currentImagePosition = arg2;
					//ΪImageSwitcher����ͼ��
					is.setImageResource(images[arg2 % images.length]);
					
				}
				public void onNothingSelected(AdapterView<?> arg0) {
					
				}});
		}
		
	}
	
	/**
	 * �Զ���Gallery��������
	 * @author Administrator
	 *
	 */
	class ImageAdapter extends BaseAdapter {

		private Context context;
		
		public ImageAdapter(Context context) {
			this.context = context;
		}
		
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		
		/**
		 * gallery������������õ�image
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context);
			iv.setImageResource(images[position%images.length]);
			iv.setAdjustViewBounds(true);
			iv.setLayoutParams(new Gallery.LayoutParams(80,80));
			iv.setPadding(15, 10, 15, 10);
			return iv;
			
		}
		
	}

	
	public View makeView() {
		ImageView view = new ImageView(this);
		view.setBackgroundColor(0xff000000);
		view.setScaleType(ScaleType.FIT_CENTER);
		view.setLayoutParams(new ImageSwitcher.LayoutParams(90,90));
		return view;
	}
	
	public void initImageChooseDialog() {
		if(imageChooseDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("��ѡ��ͼ��")
			.setView(imageChooseView).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					imageChanged = true;
					previousImagePosition = currentImagePosition;
					imageButton.setImageResource(images[currentImagePosition%images.length]);
				}
			})
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					currentImagePosition = previousImagePosition;
					
				}
			});
			imageChooseDialog = builder.create();
		}
	}
	
	/**
	 * ���˳���ʱ�򣬻�����Դ
	 */
	protected void onDestroy() {
		if(is != null) {
			is = null;
		}
		if(gallery != null) {
			gallery = null;
		}
		if(imageChooseDialog != null) {
			imageChooseDialog = null;
		}
		if(imageChooseView != null) {
			imageChooseView = null;
		}
		if(imageButton != null) {
			imageButton = null;
		}
		
		super.onDestroy();
	}
}
