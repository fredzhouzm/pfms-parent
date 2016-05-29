package com.pfms.service.worker;

import com.pfms.dao.mybatis.dao.SequenceMapper;
import com.pfms.dao.mybatis.model.Sequence;
import com.pfms.dao.mybatis.model.SequenceExample;
import com.pfms.service.boss.ISequenceService;
import com.pfms.util.Constants;
import com.pfms.util.PersonalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Fred on 2016/1/3.
 */
@Scope("singleton")
@Service
public class SequenceServiceImp implements ISequenceService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    //一级科目编号的当前ID序列号，每次递增的跨度和当前最大能取的序列号
    private static Integer pro_one_id_curval = -1;
    private static Integer pro_one_id_maxval = -1;
    private static Integer pro_one_id_midval = 0;

    //二级科目编号的当前ID序列号，每次递增的跨度和当前最大能取的序列号
    private static Integer pro_two_id_curval = -1;
    private static Integer pro_two_id_maxval = -1;
    private static Integer pro_two_id_midval = 0;

    //单据编号的当前ID序列号，每次递增的跨度和当前最大能取的序列号
    private static Integer form_id_curval = -1;
    private static Integer form_id_maxval = -1;
    private static Integer form_id_midval = 0;

    private static String dateStr = "";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    SequenceMapper sequenceMapper;
    @Autowired
    PersonalUtil personalUtil;

    //取各种ID，现有包括记账单据ID，一级科目ID和二级科目ID
    @Transactional
    public String getId(String type) {
        //判定日期是否为当天，如果不为当天，则更新
        String now = simpleDateFormat.format(new Date());
        if (!dateStr.equals(now)) {
            dateStr = now;
            if (getSeqByKey(Constants.SEQ_NAME_FORM, dateStr) == null) {
                insertOprPack(Constants.SEQ_NAME_FORM, dateStr, 0, Constants.FORM_INCVAL_HALF * 2);
                form_id_curval = 0;
                form_id_maxval = Constants.FORM_INCVAL_HALF * 2;
                form_id_midval = form_id_maxval - Constants.FORM_INCVAL_HALF;
            }
            if (getSeqByKey(Constants.SEQ_NAME_PRO_ONE, dateStr) == null) {
                insertOprPack(Constants.SEQ_NAME_PRO_ONE, dateStr, 0, Constants.PRO_ONE_INCVAL_HALF * 2);
                pro_one_id_curval = 0;
                pro_one_id_maxval = Constants.PRO_ONE_INCVAL_HALF * 2;
                pro_one_id_midval = pro_one_id_maxval - Constants.PRO_ONE_INCVAL_HALF;
            }
            if (getSeqByKey(Constants.SEQ_PRO_TWO_ID, dateStr) == null) {
                insertOprPack(Constants.SEQ_NAME_PRO_TWO, dateStr, 0, Constants.PRO_TWO_INCVAL_HALF * 2);
                pro_two_id_curval = 0;
                pro_two_id_maxval = Constants.PRO_TWO_INCVAL_HALF * 2;
                pro_two_id_midval = pro_two_id_maxval - Constants.PRO_TWO_INCVAL_HALF;
            }
        }
        if (type != null && Constants.SEQ_FORMID.equals(type)) {
            if (form_id_curval < 0 || form_id_maxval < 0) {
                Sequence sequence = getSeqByKey(Constants.SEQ_NAME_FORM, dateStr);
                form_id_curval = sequence.getCurrentVal();
                form_id_maxval = sequence.getCurrentVal() + Constants.FORM_INCVAL_HALF * 2;
                form_id_midval = form_id_maxval - Constants.FORM_INCVAL_HALF;
                updateSeqByObj(Constants.SEQ_NAME_FORM, dateStr, form_id_maxval + 1, Constants.FORM_INCVAL_HALF * 2);
            } else {
                if (form_id_curval >= form_id_midval) {
                    Sequence sequence = getSeqByKey(Constants.SEQ_NAME_FORM, dateStr);
                    form_id_maxval = sequence.getCurrentVal() + Constants.FORM_INCVAL_HALF * 2;
                    form_id_midval = form_id_maxval - Constants.FORM_INCVAL_HALF;
                    updateSeqByObj(Constants.SEQ_NAME_FORM, dateStr, form_id_maxval + 1, Constants.FORM_INCVAL_HALF * 2);
                }
            }
            return dateStr + personalUtil.intToString(form_id_curval++, 12);
        } else if (type != null && Constants.SEQ_PRO_ONE_ID.equals(type)) {
            if (pro_one_id_curval < 0 || pro_one_id_maxval < 0) {
                Sequence sequence = getSeqByKey(Constants.SEQ_NAME_PRO_ONE, dateStr);
                pro_one_id_curval = sequence.getCurrentVal();
                pro_one_id_maxval = sequence.getCurrentVal() + Constants.PRO_ONE_INCVAL_HALF * 2;
                pro_one_id_midval = pro_one_id_maxval - Constants.PRO_ONE_INCVAL_HALF;
                updateSeqByObj(Constants.SEQ_NAME_PRO_ONE, dateStr, pro_one_id_maxval + 1, Constants.PRO_ONE_INCVAL_HALF * 2);
            } else {
                if (pro_one_id_curval >= pro_one_id_midval) {
                    Sequence sequence = getSeqByKey(Constants.SEQ_NAME_PRO_ONE, dateStr);
                    pro_one_id_maxval = sequence.getCurrentVal() + Constants.PRO_ONE_INCVAL_HALF * 2;
                    pro_one_id_midval = pro_one_id_maxval - Constants.PRO_ONE_INCVAL_HALF;
                    updateSeqByObj(Constants.SEQ_NAME_PRO_ONE, dateStr, pro_one_id_maxval + 1, Constants.PRO_ONE_INCVAL_HALF * 2);
                }
            }
            return dateStr + personalUtil.intToString(pro_one_id_curval++, 12);
        } else if (type != null && Constants.SEQ_PRO_TWO_ID.equals(type)) {
            if (pro_two_id_curval < 0 || pro_two_id_maxval < 0) {
                Sequence sequence = getSeqByKey(Constants.SEQ_NAME_PRO_TWO, dateStr);
                pro_two_id_curval = sequence.getCurrentVal();
                pro_two_id_maxval = sequence.getCurrentVal() + Constants.PRO_TWO_INCVAL_HALF * 2;
                pro_two_id_midval = pro_two_id_maxval - Constants.PRO_TWO_INCVAL_HALF;
                updateSeqByObj(Constants.SEQ_NAME_PRO_TWO, dateStr, pro_two_id_maxval + 1, Constants.PRO_TWO_INCVAL_HALF * 2);
            } else {
                if (pro_two_id_curval >= pro_two_id_midval) {
                    Sequence sequence = getSeqByKey(Constants.SEQ_NAME_PRO_TWO, dateStr);
                    pro_two_id_maxval = sequence.getCurrentVal() + Constants.PRO_TWO_INCVAL_HALF * 2;
                    pro_two_id_midval = pro_two_id_maxval - Constants.PRO_TWO_INCVAL_HALF;
                    updateSeqByObj(Constants.SEQ_NAME_PRO_TWO, dateStr, pro_two_id_maxval + 1, Constants.PRO_TWO_INCVAL_HALF * 2);
                }
            }
            return dateStr + personalUtil.intToString(pro_two_id_curval++, 17);
        } else {
            return null;
        }
    }

    @Override
    public Sequence getSeqByKey(String name, String dateStr) {
        SequenceExample sequenceExample = new SequenceExample();
        sequenceExample.createCriteria().andSeqNameEqualTo(name).andDateEqualTo(dateStr);
        return getSeqByExample(sequenceExample);
    }

    @Override
    public Sequence getSeqByExample(SequenceExample sequenceExample) {
        List<Sequence> sequences = sequenceMapper.selectByExample(sequenceExample);
        if (sequences.isEmpty()) {
            return null;
        } else {
            return sequences.get(0);
        }
    }

    @Override
    public void updateSeqBySeq(Sequence sequence) {
        sequenceMapper.updateByPrimaryKey(sequence);
    }

    @Override
    public void updateSeqByObj(String name, String dateStr, Integer curval, Integer incval) {
        Sequence sequenceForUpdate = new Sequence();
        sequenceForUpdate.setSeqName(name);
        sequenceForUpdate.setDate(dateStr);
        sequenceForUpdate.setCurrentVal(curval);
        sequenceForUpdate.setIncrementVal(incval);
        updateSeqBySeq(sequenceForUpdate);
    }

    @Override
    public void insertSeq(Sequence sequence) {
        sequenceMapper.insert(sequence);
    }

    public void insertOprPack(String name, String dateStr, Integer curval, Integer incval) {
        Sequence sequenceForInsert = new Sequence();
        sequenceForInsert.setSeqName(name);
        sequenceForInsert.setDate(dateStr);
        sequenceForInsert.setCurrentVal(curval);
        sequenceForInsert.setIncrementVal(incval);
        insertSeq(sequenceForInsert);
    }
}
