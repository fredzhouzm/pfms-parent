package com.pfms.service.boss;

import com.pfms.dao.mybatis.model.Sequence;
import com.pfms.dao.mybatis.model.SequenceExample;

/**
 * Created by Fred on 2016/1/3.
 */
public interface ISequenceService {

    Sequence getSeqByKey(String name, String dateStr);

    Sequence getSeqByExample(SequenceExample sequenceExample);

    void updateSeqBySeq(Sequence sequence);

    void updateSeqByObj(String name, String dateStr, Integer curval, Integer incval);

    void insertSeq(Sequence sequence);

    public String getId(String type);
}
