package openfasttrack.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specification items with links that can be followed.
 */
public class LinkedSpecificationItem
{

    private final SpecificationItem item;
    private final Map<LinkStatus, List<LinkedSpecificationItem>> links = new HashMap<LinkStatus, List<LinkedSpecificationItem>>();

    /**
     * Create a new instance of class {@link LinkedSpecificationItem}.
     *
     * @param item
     *            the actual specification item that is at the center of the
     *            links
     */
    public LinkedSpecificationItem(final SpecificationItem item)
    {
        this.item = item;
    }

    public SpecificationItemId getId()
    {
        return this.item.getId();
    }

    /**
     * Get the specification item.
     *
     * @return the specification item
     */
    public SpecificationItem getItem()
    {
        return this.item;
    }

    /**
     * Add a link to another item with a status
     *
     * @param item
     *            the item to be linked to
     * @param status
     *            the link status
     */
    public void addLinkToItemWithStatus(final LinkedSpecificationItem item, final LinkStatus status)
    {
        List<LinkedSpecificationItem> linksWithStatus = this.links.get(status);
        if (linksWithStatus == null)
        {
            linksWithStatus = new ArrayList<>();
            this.links.put(status, linksWithStatus);
        }
        linksWithStatus.add(item);
    }

    /**
     * Get all links to the items by item status..
     *
     * @return the covered items
     */
    public List<LinkedSpecificationItem> getLinksByStatus(final LinkStatus status)
    {
        final List<LinkedSpecificationItem> linksWithStatus = this.links.get(status);
        return (linksWithStatus == null) ? Collections.<LinkedSpecificationItem> emptyList()
                : linksWithStatus;
    }

    /**
     * Get the ID of the items this {@link LinkedSpecificationItem} covers.
     *
     * @return the list of IDs
     */
    public List<SpecificationItemId> getCoveredIds()
    {
        return this.getItem().getCoveredIds();
    }
}