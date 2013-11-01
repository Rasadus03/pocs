
These are some brief notes to document my process for setting up the headers and footers.

1. Added the header and footer images to the images folder under common.  The width of the images
should never exceed 1024 (the width of the page)

2. The footer.txt and banner.txt files were created.  These files were used just be generated html with 
the embeded images.

3.  Run the "ad" command on the files in (2)

4.  Open the resulting html files and delete the first 820 lines.  Also delete the last 10 or so line from the file.

5.  In the conf folder, the html5.conf file contains include macros for both of these two html files in (4)

6.  Now the other documents in this course will automatcally receive the standard header and footer
without the need for any markup content.
