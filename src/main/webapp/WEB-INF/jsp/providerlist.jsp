<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="./common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>会员管理页面</span>
        </div>
        <div class="search">
        	<form method="get" action="provider.do">
				<input name="method" value="query" type="hidden">
				<span>会员编码：</span>
				<input name="queryProCode" type="text" value="${queryProCode }">
				
				<span>会员名称：</span>
				<input name="queryProName" type="text" value="${queryProName }">
				
				<input type="hidden" name="pageIndex" value="1"/>
				<input value="查 询" type="submit" id="searchbutton">
				<a href="providerALL.do?method=add">添加会员</a>
			</form>
        </div>
        <!--会员操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="10%">会员编码</th>
                <th width="20%">会员名称</th>
                <th width="10%">联系人</th>
                <th width="10%">联系电话</th>
                <th width="10%">积分</th>
                <th width="10%">创建时间</th>
                <th width="30%">操作</th>
            </tr>
            <c:forEach var="provider" items="${providerList }" varStatus="status">
				<tr>
					<td>
					<span>${provider.procode }</span>
					</td>
					<td>
					<span>${provider.proname }</span>
					</td>
					<td>
					<span>${provider.procontact}</span>
					</td>
					<td>
					<span>${provider.prophone}</span>
					</td>
					<td>
					<span>${provider.profax}</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${provider.creationdate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proname }><img src="statics/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyProvider" href="javascript:;" proid=${provider.id } proname=${provider.proname }><img src="statics/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteProvider" href="javascript:;" proid=${provider.id } proname=${provider.proname }><img src="statics/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
        </table>
		<input type="hidden" id="totalPageCount" value="${page.totalPageCount}"/>
		  	<c:import url="rollpage.jsp">
	          	<c:param name="totalCount" value="${page.totalCount}"/>
	          	<c:param name="currentPageNo" value="${page.currentPageNo}"/>
	          	<c:param name="totalPageCount" value="${page.totalPageCount}"/>
          	</c:import>
    </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该会员吗？</p>
           <a href="#" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>

<%@include file="./common/foot.jsp" %>
<script type="text/javascript" src="statics/js/providerlist.js"></script>
