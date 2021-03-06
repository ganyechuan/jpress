/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.ui.freemarker.tag;

import com.jfinal.plugin.activerecord.Page;

import io.jpress.core.render.freemarker.JTag;
import io.jpress.model.Comment;
import io.jpress.model.Content;
import io.jpress.model.query.CommentQuery;

/**
 * @title Content 标签
 * @author Michael Yang （http://www.yangfuhai.com）
 * @version 1.0
 * @created 2016年2月19日
 * 
 *          使用方法：<br />
 *          <@jp_commentpage page="" pagesize="" module="article" orderby ><br>
 *          <br>
 *          <#list page.getList() as content><br>
 *          ${content.id} : ${content.title!} <br>
 *          </#list><br>
 *          <br>
 *          </@jp_commentpage>
 * 
 */
public class CommentPageTag extends JTag {

	Content content;
	int pageNumber;

	public CommentPageTag(Content content, int pageNumber) {
		this.content = content;
		this.pageNumber = pageNumber;
	}

	@Override
	public void onRender() {

		int pageSize = getParamToInt("pagesize", 10);

		Page<Comment> page = CommentQuery.paginateByContentId(pageNumber, pageSize, content.getId());
		setVariable("page", page);

		CommentPaginateTag cpt = new CommentPaginateTag(page, content);
		setVariable("pagination", cpt);

		renderBody();
	}

}
