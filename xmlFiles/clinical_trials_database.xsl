<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Clinical Trials Database</title>

                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f9f9;
                        color: #333333;
                        margin: 30px;
                    }

                    h1 {
                        text-align: center;
                        color: #1f6f78;
                        margin-bottom: 10px;
                    }

                    h2 {
                        color: #2c8c99;
                        margin-top: 35px;
                        border-bottom: 2px solid #2c8c99;
                        padding-bottom: 5px;
                    }

                    .subtitle {
                        text-align: center;
                        color: #555555;
                        margin-bottom: 35px;
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                        background-color: white;
                        margin-bottom: 30px;
                        box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.08);
                    }

                    th {
                        background-color: #1f6f78;
                        color: white;
                        padding: 10px;
                        text-align: center;
                    }

                    td {
                        border: 1px solid #cccccc;
                        padding: 8px;
                        text-align: center;
                    }

                    tr:nth-child(even) {
                        background-color: #eef7f7;
                    }

                    .empty {
                        color: #999999;
                        font-style: italic;
                    }
                </style>
            </head>

            <body>
                <h1>Clinical Trials Database</h1>
                <p class="subtitle">XML database transformed into HTML using XSLT</p>

                <!-- PATIENTS -->
                <h2>Patients</h2>
                <table>
                    <tr>
                        <th>Patient ID</th>
                        <th>Name</th>
                        <th>Results</th>
                        <th>Trial ID</th>
                        <th>Hospital ID</th>
                        <th>Description ID</th>
                    </tr>

                    <xsl:for-each select="ClinicalTrials/Patients/Patient">
                        <tr>
                            <td><xsl:value-of select="@patientsId"/></td>
                            <td><xsl:value-of select="@patientName"/></td>
                            <td><xsl:value-of select="@results"/></td>
                            <td><xsl:value-of select="trialId"/></td>
                            <td><xsl:value-of select="hospitalId"/></td>
                            <td><xsl:value-of select="descriptionId"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <!-- DESCRIPTIONS -->
                <h2>Descriptions</h2>
                <table>
                    <tr>
                        <th>Description ID</th>
                        <th>Gender</th>
                        <th>Cause</th>
                        <th>Patient ID</th>
                    </tr>

                    <xsl:for-each select="ClinicalTrials/Descriptions/Description">
                        <tr>
                            <td><xsl:value-of select="@descriptionId"/></td>
                            <td><xsl:value-of select="@gender"/></td>
                            <td><xsl:value-of select="@cause"/></td>
                            <td><xsl:value-of select="patientId"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <!-- TRIALS -->
                <h2>Trials</h2>
                <table>
                    <tr>
                        <th>Trial ID</th>
                        <th>Name</th>
                        <th>Starting Date</th>
                        <th>Duration Days</th>
                        <th>Budget</th>
                        <th>Target Patients</th>
                    </tr>

                    <xsl:for-each select="ClinicalTrials/Trials/Trial">
                        <tr>
                            <td><xsl:value-of select="@trialId"/></td>
                            <td><xsl:value-of select="@trialName"/></td>
                            <td><xsl:value-of select="@startingDate"/></td>
                            <td><xsl:value-of select="@durationDays"/></td>
                            <td><xsl:value-of select="@budget"/></td>
                            <td><xsl:value-of select="@targetPatients"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <!-- DOCTORS -->
                <h2>Doctors</h2>
                <table>
                    <tr>
                        <th>Doctor ID</th>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>Specialty</th>
                        <th>Hospital ID</th>
                        <th>Trial ID</th>
                    </tr>

                    <xsl:for-each select="ClinicalTrials/Doctors/Doctor">
                        <tr>
                            <td><xsl:value-of select="@doctorId"/></td>
                            <td><xsl:value-of select="@doctorName"/></td>
                            <td><xsl:value-of select="@doctorGender"/></td>
                            <td><xsl:value-of select="@doctorSpecialty"/></td>
                            <td><xsl:value-of select="hospitalId"/></td>
                            <td><xsl:value-of select="trialId"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <!-- HOSPITALS -->
                <h2>Hospitals</h2>
                <table>
                    <tr>
                        <th>Hospital ID</th>
                        <th>Name</th>
                        <th>City</th>
                    </tr>

                    <xsl:for-each select="ClinicalTrials/Hospitals/Hospital">
                        <tr>
                            <td><xsl:value-of select="@hospitalId"/></td>
                            <td><xsl:value-of select="@hospitalName"/></td>
                            <td><xsl:value-of select="@hospitalCity"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <!-- HOSPITAL TRIALS -->
                <h2>Hospital - Trial Relations</h2>
                <table>
                    <tr>
                        <th>Trial ID</th>
                        <th>Hospital ID</th>
                    </tr>

                    <xsl:for-each select="ClinicalTrials/HospitalTrials/HospitalTrial">
                        <tr>
                            <td><xsl:value-of select="trialId"/></td>
                            <td><xsl:value-of select="hospitalId"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>