function getData(url, data){
    var json = null;
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        async:false,
        dataType: "json",
        success: function(data){
            json = data;
        }
    });
    return json;
}

function postData(url, data){
    var json = null;
    $.ajax({
        type: "post",
        url: url,
        data: data,
        async:false,
        dataType: "json",
        success: function(data){
            json = data;
        }
    });
    return json;
}

function href(url){
    window.location.href = url;
}
function reload(){
    window.location.reload(true);
}