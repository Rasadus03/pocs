#!/bin/sh

# usage:  build_asciidoc.sh /path/to/asciidoc/source/content -maxWidth=1366px -slidy


echo "############################################################################################################################### "
echo "  Allslides per module are put into a single slide show styled page."	
echo "############################################################################################################################### "

for var in $@
do
    case $var in
        -maxWidth=*)
            -maxWidth=`echo $var | cut -f2 -d\=`
            ;;
        -slidy)
            slidy=slidy
            ;;
    esac
done

if [ "x$maxWidth" = "x" ]; then
        maxWidth=1024px
fi


ASCIIDOC_LMS="asciidoc -f $JBOSS_PROJECTS/pocs/base/common/conf/html5.conf -b html5 -a max-width=$maxWidth -a icons --theme=gpe_slide_theme -a encoding=ISO-8859-1"
ASCIIDOC_ILT="asciidoc -f $JBOSS_PROJECTS/pocs/base/common/conf/slidy.conf -b html5 -a max-width=$maxWidth -a icons --theme=gpe_theme"

ROOT_OUTDIR=/tmp/generated
DIRNAME=`basename $@`
OUTDIR=target

function process_asciidoc {
    rm -rf "$ROOT_OUTDIR/$DIRNAME/$OUTDIR"
    mkdir -p "$ROOT_OUTDIR/$DIRNAME/$OUTDIR"
    for INPUT in  $@ ; do
        # If a file ending in "AllSlides.txt" then process with AsciiDoc.
        if [ -f $INPUT ] && [ `echo $INPUT | grep -c -e "AllSlides.txt$"` == 1 ]; then
            echo "Processing All from:  $INPUT"
            OUTPUT=`basename $INPUT .txt`
            if [ "x$slidy" = "x" ]; then
                $ASCIIDOC_LMS --out-file $ROOT_OUTDIR/$DIRNAME/$OUTDIR/$OUTPUT.html $INPUT;
            else
                $ASCIIDOC_ILT --out-file $ROOT_OUTDIR/$DIRNAME/$OUTDIR/$OUTPUT.html $INPUT;
            fi

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
echo "	BUILD COMPLETE:  The output of this build can be found in the $ROOT_OUTDIR/$DIRNAME/$OUTDIR directory."
echo "############################################################################################################################### "

