package soen.kgutwice.sana;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ailab on 2017-11-27.
 */
public class SubjectAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList;
    private ArrayList<SubjectItem> subjectItemList = new ArrayList<>();

    // ListViewAdapter의 생성자
    public SubjectAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return subjectItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_todoitem" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_subjectitem, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView subjectTextView = (TextView)convertView.findViewById(R.id.subject);
        TextView professorTextView = (TextView)convertView.findViewById(R.id.professor);
        TextView courseTimeTextView = (TextView)convertView.findViewById(R.id.courseTime);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        SubjectItem subjectItem = subjectItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        final String subjectName = subjectItem.getSubjectName();

        subjectTextView.setText(subjectItem.getSubjectName());
        professorTextView.setText(subjectItem.getSubjectProfessor());
        courseTimeTextView.setText(subjectItem.getSubjectcourseTime());

        final Button modifySubject = (Button)convertView.findViewById(R.id.modifySubject);
        modifySubject.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModifySubject.class);
                //intent.putExtra("userID", userID);
                intent.putExtra("subjectName", subjectName);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return subjectItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String no, String subjectName, String professorName, String courseTime, String ClassYear, String ClassSemester) {
        SubjectItem item = new SubjectItem();

        item.setNo(no);
        item.setSubjectName(subjectName);
        item.setSubjectProfessor(professorName);
        item.setSubjectcourseTime(courseTime);
        item.setClassSemester(ClassSemester);
        item.setClassYear(ClassYear);
        subjectItemList.add(item);
    }
}
