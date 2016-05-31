<#import "spring.ftl" as spring/>
<#macro head active>
<div class="menu">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#"><@spring.message 'nav.brand'/></a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li <#if active=="001">class="active"</#if>><a href="#"><@spring.message 'nav.welcome'/></a></li>
                    <li <#if active=="002">class="active"</#if>><a
                            href="/account/show.htm"><@spring.message 'nav.accounting'/></a></li>
                    <li <#if active=="003">class="active"</#if>><a href="#"><@spring.message 'nav.reports'/></a></li>
                    <li <#if active=="004">class="active"</#if>><a href="#"><@spring.message 'nav.download'/></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false"><@spring.message 'nav.settings'/><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/setting/proSet.htm"><@spring.message 'nav.settings.proSet'/></a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#"><@spring.message 'nav.settings.budgetSet'/></a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#"><@spring.message 'nav.settings.personal'/></a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <p class="navbar-text"><@spring.message 'welcome.pref'/> <#if Session["session_authentication"].nickName?exists>
                        &nbsp;${Session["session_authentication"].nickName}<#else><@spring.message 'welcome.suff'/></#if></p>
                    <a type="button" class="btn btn-default navbar-btn"><@spring.message 'button.logout'/></a>
                </ul>
            </div>
        </div>
    </nav>
</div>
</#macro>
<#macro foot>
<div class="footer">
    <p class="rights" align="center"><@spring.message 'text.copyRight'/>
</div>
</#macro>