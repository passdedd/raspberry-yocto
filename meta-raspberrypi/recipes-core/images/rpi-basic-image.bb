# Base this image on core-image-minimal
#include recipes-core/images/core-image-base.bb
include recipes-graphics/images/core-image-weston.bb

# Include modules in rootfs
IMAGE_INSTALL += " \
	kernel-modules \
        dhcp-client \
	"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"

do_image_prepend() {
    bb.warn("The image 'rpi-basic-image' is deprecated, please use 'core-image-base' instead")
}
