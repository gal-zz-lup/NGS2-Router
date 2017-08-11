'use strict';

$(document).ready(function() {
  function updateClient() {
    $.getJSON(window.location.pathname + '/update',
      function (response) {
        if (response.hasOwnProperty('src')) {
          if (curSrc !== response.src) {
            $('#peel-iframe').attr('src', response.src);
            curSrc = response.src;
          }
        }
        if (response.hasOwnProperty('progress')) {
          updateProgress(response.status, response.progress);
        }
        setTimeout(updateClient, 3000);
      });
  }

  function updateProgress(status, progressObject) {
    if (status == "WAITING") {
      if (progressObject.hasOwnProperty("valuemin")
        && progressObject.hasOwnProperty("valuemax")
        && progressObject.hasOwnProperty("value")) {
        $('#peel-progress').attr('aria-valuemin', progressObject.valuemin);
        $('#peel-progress').attr('aria-valuemax', progressObject.valuemax);
        $('#peel-progress').attr('aria-valuenow', progressObject.value);
        var percent = Math.min(Math.round((progressObject.value /progressObject.valuemax) * 100), 100);
        $('#peel-progress').css('width', percent + '%');
        $('#peel-progress').html(progressObject.value + ' participants have joined');
      }
    } else {
      // Hide the status div if the client's status is not WAITING
      $('$status-div').css('display', 'none');
    }
  }

  var curSrc = window.location.pathname + '/waiting';
  $('#peel-iframe').attr('src', curSrc);
  updateClient();
});
