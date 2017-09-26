package com.wheat7.nationalgeographic.data.model

import java.io.Serializable

/**
 * Created by wheat7 on 2017/9/16.
 * https://github.com/wheat7
 */

class Detail : Serializable{

    /**
     * counttotal : 12
     * picture : [{"id":"14457","albumid":"1601","title":"〈寻找真正的维京人〉精选","content":"波兰的维京人重演者穿上盔甲，准备上演近身战斗。维京人的残暴并非浪得虚名：斯堪地那维亚男孩从小就接受作战训练，并且在社会制约下习于血腥暴力。 ","url":"http://pic01.bdatu.com/Upload/picimg/1496230162.jpg","size":"254945","addtime":"2017-05-31 19:29:25","author":"David Guttenfelder","thumb":"http://pic01.bdatu.com/Upload/picimg/1496230162.jpg","encoded":"1","weburl":"http://","type":"pic","yourshotlink":"","copyright":"","pmd5":"9f4c2c9dd41064b5bf5bd467bac8956e","sort":"14457"}]
     */

    var counttotal: String? = null
    var picture: MutableList<Picture>? = null


}
