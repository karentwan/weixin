	var GWDateTimePicker = {
  init: function(param) {
    var now = new Date,
      CYEAR = now.getFullYear(),
      CMONTH = now.getMonth() + 1,
      CDAY = now.getDate(),
      $elements = {
        target: document.getElementById(param.targetId),
        wrapper: document.getElementById("gw--datetimepicker"),
        body: document.getElementById("gw--datetimepicker-body"),
        selectedYear: document.getElementById("gw--datetimepicker-selected-year"),
        selectedMonth: document.getElementById("gw--datetimepicker-selected-month"),
        year: document.getElementById("gw--datetimepicker-year"),
        month: document.getElementById("gw--datetimepicker-month"),
        day: document.getElementById("gw--datetimepicker-day")
      };

    $elements.selectedYear.innerHTML = CYEAR;
    $elements.selectedMonth.innerHTML = CMONTH;

    // 初始化当月的列表
    this.setDays(CYEAR, CMONTH);

    // 初始化事件
    $elements.wrapper.onclick = function(e) {
      var target = e.target,
        selectedYear, selectedMonth;

      switch (target.id) {
        case "gw--datetimepicker-selected-year":
          $elements.selectedYear.removeAttribute("data-value");
          GWDateTimePicker.setYears(parseInt($elements.selectedYear.innerHTML));
          GWDateTimePicker.showYears($elements);
          break;
        case "gw--datetimepicker-selected-month":
          $elements.selectedYear.removeAttribute("data-value");
          GWDateTimePicker.showMonths($elements);
          break;
        case "gw--datetimepicker-prev":
          GWDateTimePicker.setNav($elements, true);
          break;
        case "gw--datetimepicker-next":
          GWDateTimePicker.setNav($elements);
          break;
        case "gw--now":
          GWDateTimePicker.setDays(CYEAR, CMONTH);
          $elements.selectedYear.innerHTML = CYEAR;
          $elements.selectedMonth.innerHTML = CMONTH;
          GWDateTimePicker.showDays($elements);
          GWDateTimePicker.setOutput($elements.target, CYEAR, CMONTH, CDAY);
          break;
      }

      switch (target.className) {
        case "gw--year":
          $elements.selectedYear.innerHTML = target.innerHTML;
          GWDateTimePicker.showMonths($elements);
          break;
        case "gw--month":
          $elements.selectedMonth.innerHTML = target.innerHTML;
          GWDateTimePicker.setDays($elements.selectedYear.innerHTML, $elements.selectedMonth.innerHTML);

          GWDateTimePicker.showDays($elements);
          break;
        case "gw--day":
          GWDateTimePicker.setOutput($elements.target, $elements.selectedYear.innerHTML, $elements.selectedMonth.innerHTML, target.innerHTML);
          break;
        case "prev gw--day":
          selectedYear = $elements.selectedYear.innerHTML;
          selectedMonth = $elements.selectedMonth.innerHTML;
          selectedMonth--;

          if (selectedMonth < 1) {
            selectedYear--;
            selectedMonth = 12;
          }

          GWDateTimePicker.setOutput($elements.target, selectedYear, selectedMonth, target.innerHTML);
          break;
        case "next gw--day":
          selectedYear = $elements.selectedYear.innerHTML;
          selectedMonth = $elements.selectedMonth.innerHTML;
          selectedMonth++;

          if (selectedMonth > 12) {
            selectedYear++;
            selectedMonth = 1;
          }

          GWDateTimePicker.setOutput($elements.target, selectedYear, selectedMonth, target.innerHTML);
          break;
      }
    };
  },
  setYears: function(year) {
    var i = year + 6,
      html = "",
      col = 0;
    year -= 6;
    while (year < i) {
      if (col === 0) {
        html += "<tr>";
      }

      html += '<td class="gw--year">' + year + '</td>';

      if (col === 3) {
        html += "</tr>";
        col = -1;
      }

      year++;
      col++;
    }

    document.getElementById("gw--datetimepicker-year-body").innerHTML = html;
  },
  setDays: function(year, month) {
    /**
     * @param year Int
     * @param month Int 必须是 1-12
     * */

    year = parseInt(year);
    month = parseInt(month);

    var DAYARR = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
      totalDays = DAYARR[--month], // month 需要减1
      day = 1,
      week = 0,
      line = 0,
      innerHTML = "",
      $wrap = document.getElementById("gw--datetimepicker-day-body"),
      firstDateWeek = new Date(Date.UTC(year, month)).getDay();

    if (month === 1 && ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)) {
      totalDays++; // 闰年2月29天
    }

    var daysNotThisMonth = DAYARR[(month + 12) % 12] - firstDateWeek;

    // 计算日期列表
    while (week < 7) {
      if (week == 0) {
        innerHTML += '<tr>';
      }

      if (line == 0) {
        // 单独计算第一行
        while (week++ < firstDateWeek) {
          innerHTML += '<td class="prev gw--day">' + (daysNotThisMonth + week) + '</td>';
        }

        week = firstDateWeek;

        while (week++ < 7) {
          innerHTML += '<td class="gw--day">' + day++ + '</td>';
        }

        daysNotThisMonth = 1;
        week = 0;
        line++;
        continue;
      } else {
        if (day <= totalDays) {
          innerHTML += '<td class="gw--day">' + day++ + '</td>';
        } else {
          innerHTML += '<td class="next gw--day">' + daysNotThisMonth++ + '</td>';
        }
      }

      if (week == 6) {
        innerHTML += '</tr>';
        line++;
      }

      line < 6 && week == 6 ? week = 0 : week++;
    }

    $wrap.innerHTML = innerHTML;
  },
  setNav: function($con, isPrev) {
    var year = parseInt($con.selectedYear.innerHTML),
      month = parseInt($con.selectedMonth.innerHTML);

    switch ($con.body.getAttribute("data-selected")) {
      case "year":
        var yearView = parseInt($con.selectedYear.getAttribute("data-value"));

        if (isPrev === true) {
          yearView ? yearView -= 12 : yearView = year - 12;
        } else {
          yearView ? yearView += 12 : yearView = year + 12;
        }

        $con.selectedYear.setAttribute("data-value", yearView.toString());
        GWDateTimePicker.setYears(yearView);
        break;
      case "day":
        isPrev ? month-- : month++;

        if (isPrev === true && month < 1) {
          $con.selectedYear.innerHTML = --year;
          month = 12;
          $con.selectedMonth.innerHTML = 12;
        } else if (isPrev !== true && month > 12) {
          $con.selectedYear.innerHTML = ++year;
          month = 1;
          $con.selectedMonth.innerHTML = 1;
        } else {
          $con.selectedMonth.innerHTML = month;
        }

        GWDateTimePicker.setDays(year, month);
        break;
    }
  },
  setOutput: function($target, year, month, day) {
    $target.value = year + "年 " + month + "月 " + day + "日";
  },
  showYears: function($con) {
    $con.year.style.display = "block";
    $con.month.style.display = "none";
    $con.day.style.display = "none";
    $con.body.setAttribute("data-selected", "year");
  },
  showMonths: function($con) {
    $con.year.style.display = "none";
    $con.month.style.display = "block";
    $con.day.style.display = "none";
    $con.body.setAttribute("data-selected", "month");
  },
  showDays: function($con) {
    $con.year.style.display = "none";
    $con.month.style.display = "none";
    $con.day.style.display = "block";
    $con.body.setAttribute("data-selected", "day");
  }
};

GWDateTimePicker.init({
  targetId: "date-container"
});
