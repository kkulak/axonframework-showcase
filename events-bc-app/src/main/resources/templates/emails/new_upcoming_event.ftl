<html>
    <body>
        <span>Hello!</span>
        <br/>
        <br/>

        <span>
            We're about to organize the following event:
        </span>
        <br/>

        <h4>
        ${eventName}
        </h4>
        <br/>

        <#if imageUrl??>
        <img src="${imageUrl}">
        </#if>
        <br/>

        <span>
        ${eventDescription}
        </span>
        <br/>
        <br/>

        <span>
            Click <a href="http://rossum.knbit.edu.pl:8080/#/events/member/enrollment">here</a> to enroll!
        </span>
    </body>
</html>