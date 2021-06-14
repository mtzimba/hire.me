const URL_APP = "http://localhost:8080/u/";


$(document).ready(function() {
    updateTopTenTable();
	clenFields();

    $('#short-me').click(function () {
        save()
    });
});

function clenFields() {
  $('#text-url').val('');
  $('#text-alias').val('');
}

function updateTopTenTable() {
  $.ajax({
        url: URL_APP
    }).then(function(data) {
       var topTenTable = '';
       $.each( data, function( key, value ) {
          topTenTable += '<tr>';
          topTenTable += '<td><span onclick="window.open(\''+value+'\', \'_blank\');">' + value + '</span></td>';
          topTenTable += '</tr>';
     });
    $('#topTen').html(topTenTable);
    });
}


function redirect(shortUrl) { 
  window.location.href = shortUrl;
}

function save() {
  var url = $('#text-url').val();
  var alias =  $('#text-alias').val();

  var data = 'url='+url;
  if (alias && alias !== '') {
	data+='&CUSTOM_ALIAS=' + alias;
  }

  $('.form-field').removeClass('required');
  hideMessage();

  if (validate(url)) {
    $.ajax({
          url: URL_APP,
 		  data: data,
          type: 'put'
    }).done(function(data) {
      displayMessage(true, 'URL CREATED: ' + '<span onclick="window.open(\''+data.url+'\',\'_blank\');">' + data.url + '</span>');
      updateTopTenTable();
	  clenFields();
    }).fail(function(jqXHR, textStatus, error) {
      displayMessage(false, jqXHR.responseJSON.err_code + ' - ' + jqXHR.responseJSON.description);
    });
  }else{
    $('#text-url').addClass('required');
  }
}

function hideMessage(){
  $('#div-message').removeAttr('class');
  $('#div-message').attr('class');
  $('#div-message').html('');
}

function displayMessage(statusMessage, message) {
  hideMessage();
  $('#div-message').html(message);
  if (statusMessage) {
    $('#div-message').addClass('alert').addClass('alert-success');
  } else {
    $('#div-message').addClass('alert').addClass('alert-danger');
  }
}

function validate(value) {
    if (value == null || $.trim(value) == '') {
      displayMessage(false, 'URL IS REQUIRED');
      return false;
    }
    return true;
}
