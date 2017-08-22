/*
 ************************************************************************
 *******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
 **************  CENTRE CANADIEN DE DONNÉES ASTRONOMIQUES  **************
 *
 *  (c) 2017.                            (c) 2017.
 *  Government of Canada                 Gouvernement du Canada
 *  National Research Council            Conseil national de recherches
 *  Ottawa, Canada, K1A 0R6              Ottawa, Canada, K1A 0R6
 *  All rights reserved                  Tous droits réservés
 *
 *  NRC disclaims any warranties,        Le CNRC dénie toute garantie
 *  expressed, implied, or               énoncée, implicite ou légale,
 *  statutory, of any kind with          de quelque nature que ce
 *  respect to the software,             soit, concernant le logiciel,
 *  including without limitation         y compris sans restriction
 *  any warranty of merchantability      toute garantie de valeur
 *  or fitness for a particular          marchande ou de pertinence
 *  purpose. NRC shall not be            pour un usage particulier.
 *  liable in any event for any          Le CNRC ne pourra en aucun cas
 *  damages, whether direct or           être tenu responsable de tout
 *  indirect, special or general,        dommage, direct ou indirect,
 *  consequential or incidental,         particulier ou général,
 *  arising from the use of the          accessoire ou fortuit, résultant
 *  software.  Neither the name          de l'utilisation du logiciel. Ni
 *  of the National Research             le nom du Conseil National de
 *  Council of Canada nor the            Recherches du Canada ni les noms
 *  names of its contributors may        de ses  participants ne peuvent
 *  be used to endorse or promote        être utilisés pour approuver ou
 *  products derived from this           promouvoir les produits dérivés
 *  software without specific prior      de ce logiciel sans autorisation
 *  written permission.                  préalable et particulière
 *                                       par écrit.
 *
 *  This file is part of the             Ce fichier fait partie du projet
 *  OpenCADC project.                    OpenCADC.
 *
 *  OpenCADC is free software:           OpenCADC est un logiciel libre ;
 *  you can redistribute it and/or       vous pouvez le redistribuer ou le
 *  modify it under the terms of         modifier suivant les termes de
 *  the GNU Affero General Public        la “GNU Affero General Public
 *  License as published by the          License” telle que publiée
 *  Free Software Foundation,            par la Free Software Foundation
 *  either version 3 of the              : soit la version 3 de cette
 *  License, or (at your option)         licence, soit (à votre gré)
 *  any later version.                   toute version ultérieure.
 *
 *  OpenCADC is distributed in the       OpenCADC est distribué
 *  hope that it will be useful,         dans l’espoir qu’il vous
 *  but WITHOUT ANY WARRANTY;            sera utile, mais SANS AUCUNE
 *  without even the implied             GARANTIE : sans même la garantie
 *  warranty of MERCHANTABILITY          implicite de COMMERCIALISABILITÉ
 *  or FITNESS FOR A PARTICULAR          ni d’ADÉQUATION À UN OBJECTIF
 *  PURPOSE.  See the GNU Affero         PARTICULIER. Consultez la Licence
 *  General Public License for           Générale Publique GNU Affero
 *  more details.                        pour plus de détails.
 *
 *  You should have received             Vous devriez avoir reçu une
 *  a copy of the GNU Affero             copie de la Licence Générale
 *  General Public License along         Publique GNU Affero avec
 *  with OpenCADC.  If not, see          OpenCADC ; si ce n’est
 *  <http://www.gnu.org/licenses/>.      pas le cas, consultez :
 *                                       <http://www.gnu.org/licenses/>.
 *
 *
 ************************************************************************
 */

package ca.nrc.cadc.nameresolver;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.Writer;


public class TargetDataXMLWriter implements TargetDataWriter
{
    private final String RESULT_ROOT = "result";


    /**
     * Write out the target data to the given writer.
     *
     * @param targetData The target data instance.
     * @param request    The target request.
     * @param writer     The writer instance.
     * @throws Exception Any writing errors.
     */
    @Override
    public void write(final TargetData targetData, final TargetResolverRequest request,
                      final Writer writer) throws Exception
    {
        final Element root = new Element(RESULT_ROOT);

        if (targetData.isError())
        {
            root.addContent(new Element(ERROR_KEY).setText(targetData.getErrorMessage()));
        }

        root.addContent(new Element(TARGET_KEY).setText(targetData.getTarget()));
        root.addContent(new Element(SERVICE_KEY).setText(targetData.getDatabase() + "(" + targetData.getHost() + ")"));
        root.addContent(new Element(COORDSYS_KEY).setText(targetData.getCoordsys()));
        root.addContent(new Element(RA_KEY).setText(Double.toString(targetData.getRA())));
        root.addContent(new Element(DEC_KEY).setText(Double.toString(targetData.getDEC())));

        if (request.detail == Detail.MAX)
        {
            root.addContent(new Element(ONAME_KEY ).setText((targetData.getObjectName() != null)
                                                            ? targetData.getObjectName() : ""));
            root.addContent(new Element(OTYPE_KEY ).setText((targetData.getObjectType() != null)
                                                            ? targetData.getObjectType() : ""));
            root.addContent(new Element(MTYPE_KEY ).setText((targetData.getMorphologyType() != null)
                                                            ? targetData.getMorphologyType() : ""));
        }

        root.addContent(new Element(TIME_KEY).setText(Long.toString(targetData.getQueryTime())));

        final XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(org.jdom2.output.Format.getPrettyFormat());
        xmlOutputter.output(new Document(root), writer);
    }
}
