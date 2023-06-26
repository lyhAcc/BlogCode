package com.example.viewtest1.customviewtest_4category.customviewgroup.example2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.viewtest1.R;
import com.example.viewtest1.util.Utils;

import java.util.ArrayList;

//https://www.jianshu.com/p/138b98095778
public class NinePhotoView extends ViewGroup {

    public static final int MAX_PHOTO_NUMBER = 9;

    private int[] constImageIds = { R.drawable.pic0, R.drawable.pic1,
            R.drawable.pic2, R.drawable.pic3, R.drawable.pic4,
            R.drawable.pic5, R.drawable.pic6, R.drawable.pic7,
            R.drawable.pic8 };

    // horizontal space among children views
    int hSpace = Utils.dp2px(getContext(), 10);
    // vertical space among children views
    int vSpace = Utils.dp2px(getContext(), 10);

    // every child view width and height
    int childWidth = 0;
    int childHeight = 0;

    // store images res id
    ArrayList<Integer> mImageResArrayList = new ArrayList<Integer>(9);

    private View addPhotoView;

    public NinePhotoView(Context context) {
        super(context);
    }

    public NinePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //自定义属性（暂略）

        //初始化添加+号按钮
        addPhotoView = new View(context);
        addView(addPhotoView);
        mImageResArrayList.add(new Integer(-1));  //-1表示任意res id
    }

    //应用自定义LayoutParams
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);

        //子view宽高一致
        childWidth = (rw - 2 * hSpace) / 3;
        childHeight = childWidth;

        //1.测所有子view
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);  //文中这句被注释了，但不影响运行效果。评论区有相关讨论

            //把子View的左上角坐标存储到我们自定义的LayoutParams的left和top二个字段中，Layout阶段会使用
            MyLayoutParams layoutParams = (MyLayoutParams) child.getLayoutParams();
            layoutParams.left = (i % 3) * (childWidth + hSpace);
            layoutParams.top = (i / 3) * (childWidth + vSpace);
        }

        //2.测自己
        int vw = rw;
        int vh = rh;
        if (childCount < 3) {
            vw = childCount * (childWidth + hSpace);
        }
        vh = ((childCount + 3) / 3) * (childWidth + vSpace);
        setMeasuredDimension(vw, vh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MyLayoutParams layoutParams = (MyLayoutParams) child.getLayoutParams();
            child.layout(layoutParams.left, layoutParams.top, layoutParams.left + childWidth, layoutParams.top + childHeight);

            if (i == mImageResArrayList.size() - 1 && mImageResArrayList.size() != MAX_PHOTO_NUMBER) {  //图片不满9张且i为”添加图片“
                child.setBackgroundResource(R.drawable.add_photo);
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPhotoBtnClick();
                    }
                });
            } else {  //其他情况
                child.setBackgroundResource(constImageIds[i]);
                child.setOnClickListener(null);
            }
        }
    }

    private void addPhotoBtnClick() {
        final CharSequence[] items = { "Take Photo", "Photo from gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addPhoto();
            }
        });
        builder.show();
    }

    private void addPhoto() {
        if (mImageResArrayList.size() < MAX_PHOTO_NUMBER) {
            View newChild = new View(getContext());
            addView(newChild);
            mImageResArrayList.add(new Integer(-1));  //-1表示任意res id

            //下面两句可不写，因addView中已调
//            requestLayout();
//            invalidate();
        }
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }
//
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//    }
}