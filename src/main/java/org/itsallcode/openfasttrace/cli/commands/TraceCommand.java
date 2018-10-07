
package org.itsallcode.openfasttrace.cli.commands;

/*-
 * #%L
 \* OpenFastTrace
 * %%
 * Copyright (C) 2016 - 2017 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.nio.file.Path;

import org.itsallcode.openfasttrace.ReportSettings;
import org.itsallcode.openfasttrace.Report;
import org.itsallcode.openfasttrace.cli.CliArguments;
import org.itsallcode.openfasttrace.core.Trace;
import org.itsallcode.openfasttrace.mode.ReportMode;

/**
 * Handler for requirement tracing CLI command.
 */
public class TraceCommand extends AbstractCommand
{
    public static final String COMMAND_NAME = "trace";

    /**
     * Create a {@link TraceCommand}.
     * 
     * @param arguments
     *            command line arguments.
     */
    public TraceCommand(final CliArguments arguments)
    {
        super(arguments);
    }

    @Override
    public boolean run()
    {
        final Report reporter = createReporter();
        final Trace trace = report(reporter);
        return trace.hasNoDefects();
    }

    private Report createReporter()
    {
        final ReportSettings settings = convertCommandLineArgumentsToReportSettings();
        final Report reporter = new ReportMode();
        reporter.addInputs(toPaths(this.arguments.getInputs())) //
                .setFilters(createFilterSettingsFromArguments()) //
                .configure(settings);
        return reporter;
    }

    private ReportSettings convertCommandLineArgumentsToReportSettings()
    {
        return new ReportSettings.Builder()
                .verbosity(this.arguments.getReportVerbosity())
                .newline(this.arguments.getNewline())
                .showOrigin(this.arguments.getShowOrigin()).build();
    }

    private Trace report(final Report reporter)
    {
        final Trace trace = reporter.trace();
        final Path outputPath = this.arguments.getOutputPath();
        if (null == outputPath)
        {
            reporter.reportToStdOut(trace);
        }
        else
        {
            reporter.reportToPath(trace, this.arguments.getOutputPath());
        }
        return trace;
    }
}
