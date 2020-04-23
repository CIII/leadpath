package com.pony.core;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * PonyLeads 2012.
 * This is a immutable wrapper to the activation MimeTpye.
 * <p>This class contains some extensions to the activation MimeType, such as the type safe enum
 * pattern, and allows for a mime type to specify allowed sub types.</p>
 *
 * @author <a href="mailto:martinholzner@gmail.com">Martin Holzner</a>
 * @version $LastChangedRevision:1343 $, $LastChangedDate:2006-01-06 18:49:26 -0500 (Fri, 06 Jan 2006) $
 * @see javax.activation.MimeType
 *      see also ftp://ftp.isi.edu/in-notes/iana/assignments/media-types/
 */
public final class MediaType {
	private static final Log LOG = LogFactory.getLog(MediaType.class);

    private static final Map<String, MediaType> allowedTypes = new HashMap<String, MediaType>();
    private static final Map<String, MediaType> supportedExtensions = new HashMap<String, MediaType>();

    /**
     * Mime type 'Any' maps to an accept mime type of '*.*' (used by IE for css, images, etc.)
     */
    public static final MediaType ANY = new MediaType("*", "*", new String[]{});

    /**
     * Mime type xhtml
     */
    public static final MediaType XHTML = new MediaType("application", "xhtml+xml", new String[]{"xhtml"});

    /**
     * Mime type html
     */
    public static final MediaType HTML = new MediaType("text", "html", new String[]{"html", "htm"}, new MediaType[]{XHTML});

    /**
     * Mime type form (application/x-www-form-urlencoded)
     */
    public static final MediaType FORM = new MediaType("application", "x-www-form-urlencoded", new String[]{});


    /**
     * Mime type json (application/json)
     */
    public static final MediaType JSON = new MediaType("application", "json", new String[]{});

    /**
     * Mime type xml
     */
    public static final MediaType XML = new MediaType("text", "xml", new String[]{"xml"}, new MediaType[]{XHTML});

    /**
     * Mime type wml
     */
    public static final MediaType WML = new MediaType("text", "vnd.wap.wml", new String[]{"wml"});

    /**
     * Mime type css
     */
    public static final MediaType CSS = new MediaType("text", "css", new String[]{"css"});

    /**
     * Mime type text
     */
    public static final MediaType TEXT = new MediaType("text", "plain", new String[]{"txt"});

    /**
     * Mime type js
     */
    public static final MediaType JS = new MediaType("text", "javascript", new String[]{"js"});

    /**
     * Mime type svg
     */
    public static final MediaType SVG = new MediaType("image", "svg+xml", new String[]{"svg"});

    /**
     * Mime type jpeg
     */
    public static final MediaType JPEG = new MediaType("image", "jpeg", new String[]{"jpeg", "jpg"});

    /**
     * Mime type gif
     */
    public static final MediaType GIF = new MediaType("image", "gif", new String[]{"gif"});

    /**
     * Mime type png
     */
    public static final MediaType PNG = new MediaType("image", "png", new String[]{"png"});

    /**
     * Mime type wbmp
     */
    public static final MediaType WBMP = new MediaType("image", "vnd.wap.wbmp", new String[]{"wbpm"});

    /**
     * Mime type rss
     */
    public static final MediaType RSS = new MediaType("application", "rss+xml", new String[]{});

    /**
     * Mime type ico (see http://filext.com/detaillist.php?extdetail=ICO)
     */
    public static final MediaType ICO = new MediaType("application", "octet-stream", new String[]{"ico"});

    /**
     * Mime type for excel spread sheets
     */
    public static final MediaType MS_EXCEL = new MediaType("application", "vnd.ms-excel", new String[]{"xls"});

    /**
     * Mime type pdf
     */
    public static final MediaType PDF = new MediaType("application", "pdf", new String[]{"pdf"});

    private MimeType m_mimeType = null;
    private MediaType[] m_allowedSubTypes;
    private Set<MediaType> allowSubTypeSet;

    /**
     * Construct a mime type instance without any allowed subtypes.
     *
     * @param primaryType the primary type of the mime type (i.e. 'text')
     * @param subType     the sub type of the mime type (i.e. 'html')
     * @param extensions
     */
    private MediaType(String primaryType, String subType, String[] extensions) {
        try {
            m_mimeType = new MimeType(primaryType, subType);
            m_allowedSubTypes = null;
            allowSubTypeSet = null;
            allowedTypes.put(m_mimeType.getBaseType(), this);
            for (String extension : extensions) {
                supportedExtensions.put(extension, this);
            }
        }
        catch (MimeTypeParseException e) {
            // +++TODO handle this , but where ?
            LOG.error(e);
        }
    }

    /**
     * Construct a mime type with the provided allowed subtypes.
     *
     * @param primaryType     the primary type of the mime type (i.e. 'text')
     * @param subType         the sub type of the mime type (i.e. 'html')
     * @param allowedSubTypes an array of <code>MediaType</code>s to allow as valid subtypes of this type
     */
    private MediaType(String primaryType, String subType, String[] extensions, MediaType[] allowedSubTypes) {
        this(primaryType, subType, extensions);
        // only if the mime type was sucessfully created
        if (m_mimeType != null) {
            m_allowedSubTypes = allowedSubTypes;
            allowSubTypeSet = new HashSet<MediaType>(Arrays.asList(allowedSubTypes));
        }
    }

    /**
     * Get the mime type for the presented string, if the string contains a valid mime type.
     *
     * @param mimeType the <code>java.lang.String</code> to parse into a <code>RegistryMimeTpye</code>
     * @return the <code>RegistryMimeTpye</code> that matches with the presented string
     * @throws MimeTypeParseException   if the presented mimetype is not supported
     * @throws IllegalArgumentException if the presented string is null or empty
     */
    public static MediaType parseMimeType(String mimeType) throws MimeTypeParseException {
        if (mimeType == null || "".equals(mimeType)) {
            throw new IllegalArgumentException("no valid mime type provided");
        }

        String type = mimeType.trim().toLowerCase();
        if (allowedTypes.keySet().contains(type)) {
            return allowedTypes.get(type);
        }

        throw new MimeTypeParseException("Type [" + mimeType + "] not supported");
    }

    /**
     * Get the mime type for the presented string.
     * <p>The string is handles as a file name extension. example: 'xml' returns MediaType.XML</p>
     *
     * @param extension the <code>java.lang.String</code> to parse into a <code>RegistryMimeTpye</code>
     * @return the <code>RegistryMimeTpye</code> that matches with the presented string
     * @throws MimeTypeParseException   if the presented mimetype is not supported
     * @throws IllegalArgumentException if the presented string is null or empty
     */
    public static MediaType parseMimeTypeByExtension(String extension) throws MimeTypeParseException {
        if (extension == null || "".equals(extension)) {
            throw new IllegalArgumentException("no valid mime type provided [" + extension + "]");
        }

        String ext = extension.trim().toLowerCase();
        if (supportedExtensions.keySet().contains(ext)) {
            return supportedExtensions.get(ext);
        }

        throw new MimeTypeParseException("Extension [" + extension + "] not supported");
    }

    /**
     * Get a list of allowed sub types for the passed mime type.
     *
     * @param mimeType the <code>RegistryMimeTpye</code> to get the list of allowed subtypes for
     * @return a <code>java.util.List</code> of <code>PortalMimeTpye</code>s
     */
    public static List<MediaType> getAllowedSubTypes(MediaType mimeType) {
        if (mimeType.m_allowedSubTypes == null) {
            return Collections.emptyList();
        }
        else {
            return Collections.unmodifiableList(Arrays.asList(mimeType.m_allowedSubTypes));
        }
    }

    /**
     * Get a list of allowed sub types for for this mime type.
     *
     * @return a <code>java.util.List</code> of <code>MediaType</code>s
     */
    public List getAllowedSubTypes() {
        if (m_allowedSubTypes == null) {
            return Collections.EMPTY_LIST;
        }
        else {
            return Collections.unmodifiableList(Arrays.asList(m_allowedSubTypes));
        }
    }

    /**
     * Return true if the allowed sub types contains the specified media type.
     *
     * @param other the sub type to test
     * @return true if it is an allowed sub type
     */
    public boolean isAllowedSubType(MediaType other) {
        if (equals(other)) {
            return true;
        }
        if (allowSubTypeSet == null) {
            return false;
        }
        return allowSubTypeSet.contains(other);
    }

    /**
     * Get the String representation of the mime type (i.e. 'text/html').
     *
     * @return the mime type as a <code>java.lang.String</code>
     * @see java.lang.Object#toString
     */
    public String toString() {
        return m_mimeType.getBaseType();
    }

    /**
     * compare the parameter with this instance and see if they are equals.
     *
     * @param o the Object to compare this instance to
     * @return true if this and the paramters o are equal
     * @see java.lang.Object#equals
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaType)) {
            return false;
        }

        final MediaType type = (MediaType) o;

        return m_mimeType.equals(type.m_mimeType);
    }

    /**
     * Get the hascode for this mime type.
     *
     * @return an int value representing this instance
     * @see java.lang.Object#hashCode
     */
    public int hashCode() {
        return m_mimeType.hashCode();
    }
}