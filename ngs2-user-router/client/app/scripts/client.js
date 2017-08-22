'use strict';

$(document).ready(function() {
  function updateClient() {
    $.getJSON(window.location.pathname + '/update',
      function (response) {
        console.log("response", response);
        var curSrc = getSrc(response);

        // If the src is different than what is currently being displayed
        if ($('#peel-iframe').attr('src') !== curSrc) {
          $('#peel-iframe').attr('src', curSrc);
        }

        if (response.hasOwnProperty('progress')) {
          updateProgress(response);
        }
        setTimeout(updateClient, 3000);
      });
  }

  function getSrc(response) {
    if (response.hasOwnProperty('src') && response.src !== '' && response.src !== null && response.src !== 'null') {
      return response.src;
    }

    if (response.hasOwnProperty('nActiveExperimentInstances') && $.isNumeric(response.nActiveExperimentInstances)) {
      if (response.nActiveExperimentInstances > 0) {
        return window.location.pathname + '/waiting';
      }
    }

    return window.location.pathname + '/no-experiments'
  }

  function updateProgress(response) {
    var status = response.status;
    var progressObject = response.progress;
    var nActiveExperimentInstances = response.nActiveExperimentInstances;
    if (status == "WAITING" && nActiveExperimentInstances > 0) {
      if (progressObject.hasOwnProperty("minValue")
        && progressObject.hasOwnProperty("maxValue")
        && progressObject.hasOwnProperty("value")) {
        $('#peel-progress').attr('aria-valuemin', progressObject.minValue);
        $('#peel-progress').attr('aria-valuemax', progressObject.maxValue);
        $('#peel-progress').attr('aria-valuenow', progressObject.value);
        var percent = Math.min(Math.round((progressObject.value /progressObject.maxValue) * 100), 100);
        $('#peel-progress').css('width', percent + '%');
        $('#peel-progress').html(progressObject.value + ' participants have joined');
      }
    } else {
      // Hide the status div if the client's status is not WAITING
      $('#status-div').css('display', 'none');
    }
  }

  updateClient();
});
