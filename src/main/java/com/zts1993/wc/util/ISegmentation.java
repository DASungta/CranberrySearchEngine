package com.zts1993.wc.util;

import org.ansj.domain.Term;

import java.util.List;

/**
 * Created by TianShuo on 2015/3/22.
 */
public interface ISegmentation {

    List<Term> parse(String input);

}
