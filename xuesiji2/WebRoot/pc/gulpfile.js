"use strict";

var gulp   = require("gulp");
var sass   = require("gulp-sass");
var uglify = require("gulp-uglify");
var browserSync = require("browser-sync").create();
var reload = browserSync.reload;

gulp.task('serve', function() {
	browserSync.init({
		server: {
			baseDir: "./"
		}
	});

	gulp.watch('./*.html').on('change',reload);
	gulp.watch('./classManage/*.html').on('change',reload);
	gulp.watch('./mobile/*.html').on('change',reload);
	gulp.watch('./mobile/detail/*.html').on('change',reload);
	gulp.watch('./js/*.js').on('change',reload);
	gulp.watch('./sass/*.scss', ['sass2css']);
	gulp.watch('./css/*.css').on('change',reload);
});

gulp.task('sass2css', function() {
    return gulp.src('sass/*.scss')
			   .pipe(sass())
			   .pipe(gulp.dest('css/'))
			   .pipe(reload({stream: true}));
});

gulp.task('default',['serve']);



