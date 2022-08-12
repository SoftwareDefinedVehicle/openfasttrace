package org.itsallcode.openfasttrace.core.cli;

/*-
 * #%L
 * OpenFastTrace Core
 * %%
 * Copyright (C) 2016 - 2019 itsallcode.org
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.itsallcode.junit.sysextensions.AssertExit;
import org.itsallcode.junit.sysextensions.ExitGuard;
import org.itsallcode.openfasttrace.api.exporter.ExporterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExitGuard.class)
class TestCliStarter
{
    @Test
    void testRunWithoutArguments()
    {
        AssertExit.assertExitWithStatus(2, () -> run());
    }

    @Test
    void testRunThrowingException()
    {
        final ExporterException exception = assertThrows(ExporterException.class,
                () -> run("trace"));
        assertThat(exception.getMessage(),
                equalTo("Found no matching reporter for output format 'plain'"));
    }

    @Test
    void testRunWithUnknownCommand()
    {
        AssertExit.assertExitWithStatus(2, () -> run("unknownCommand"));
    }

    @Test
    void testRunWithHelpCommand()
    {
        AssertExit.assertExitWithStatus(0, () -> run("help"));
    }

    private void run(final String... args)
    {
        CliStarter.main(args);
    }
}
