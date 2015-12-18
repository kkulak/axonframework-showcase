<html>
    <body>
        <span>Hello!</span>
        <br/>
        <br/>

        <span>
            We're planning to organize the following event for you:
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

        <span>
            Interested? Or do you think it's a waste of time?
        </span>
        <br/>
        <span>
            Don't forget to share your opinion <a href="http://rossum.knbit.edu.pl:8080/#/events/member/surveying">here!</a>
        </span>

    </body>
</html>