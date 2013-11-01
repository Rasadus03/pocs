#!/bin/sh
echo "############################################################################################################################### "
echo "  Allslides per module are put into a single slide show sytled page."	
echo "############################################################################################################################### "

ASCIIDOC_SLIDE="asciidoc -f $JBOSS_PROJECTS/pocs/base/common/conf/slidy.conf -b html5 -a max-width=1024px -a icons --theme=gpe_slide_theme -a encoding=ISO-8859-1"
#ASCIIDOC_SLIDE="asciidoc -b html5 -a max-width=1024px -a icons --theme=gpe_slide_theme -a encoding=ISO-8859-1"

DIRNAME=`basename $@`
OUTDIR=target

function process_asciidoc {
    rm -rf "$DIRNAME/$OUTDIR"
    mkdir -p "$DIRNAME/$OUTDIR"
    for INPUT in  $@ ; do
        # If a file ending in "AllSlides.txt" then process with AsciiDoc.
        if [ -f $INPUT ] && [ `echo $INPUT | grep -c -e "AllSlides.txt$"` == 1 ]; then
            echo "Processing All from:  $INPUT"
            OUTPUT=`basename $INPUT .txt`
            $ASCIIDOC_SLIDE --out-file $DIRNAME/$OUTDIR/$OUTPUT.html $INPUT;

	# Else if a directory, process its contents.
        elif [ -d $INPUT ] ; then
            echo "\n################## Processing directory $INPUT #####################"
	    DIRNAME=$INPUT
    	    DIRNAME=`echo "$DIRNAME" | tr -d ' '`
            process_asciidoc `ls $INPUT/*`
        fi
    done
}

process_asciidoc $@

echo "############################################################################################################################### "
echo "	BUILD COMPLETE:  The output of this build can be found in the $DIRNAME/$OUTDIR folder."
echo "############################################################################################################################### "

