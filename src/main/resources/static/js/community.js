$(document).ready(function () {

    // $("#login").click(function(){
    //     alert(window.location.href);
    // })


})

/*function login() {
    var url = window.location.href;
    $("#login").attr("href", "https://github.com/login/oauth/authorize?client_id=fb0fba96031d94894be3&redirect_uri=http://localhost:8887/callback&scope=user&state=1&redirect_url=" + url);
}*/

function post(parentId, type, conent) {
    $.ajax({
        type: "post",
        url: "/comment",
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": parentId,
            "type": type,
            "content": conent
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload("/question/" + parentId);
                /*$("#comment").hide();
                alert(response.message);*/
            } else {
                alert(response.message);
            }
        }
    })
}


/*添加问题评论数据*/
function addComment() {
    var parentId = $("#parent_id").val();
    var type = $("#type").val();
    var conent = $("#content").val();
    // alert(conent);
    post(parentId, type, conent);
};

/*添加二级评论数据*/
function addToComment() {
    var id = $("#commentId").val();
    var conent = $("#toContent").val();
    post(id, 2, conent);
}

/*展开和折叠二级评论*/
function toComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var selected = e.getAttribute("data-selected");
    if (selected) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-selected");
        e.classList.remove("active");
        $("#comment").show();

    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-selected", "in");
            e.classList.add("active");
            $("#comment").hide();
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.t.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "img-circle",
                        "style":"width: 42px;height: 42px",
                        "src": comment.tbUser.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h6/>", {
                        "class": "media-heading",
                        "html": comment.tbUser.uname
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "style":"float: right",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));

                    var meidaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "well"
                    }).append(meidaElement);
                    subCommentContainer.prepend(commentElement);
                });

            });

            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-selected", "in");
            e.classList.add("active");
            $("#comment").hide();

        }


    }


}
    
