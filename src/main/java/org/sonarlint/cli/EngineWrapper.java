/*
 * SonarLint CLI
 * Copyright (C) 2016-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarlint.cli;

import org.sonarlint.cli.analysis.SonarLintFactory;
import org.sonarlint.cli.report.ReportFactory;

import java.nio.file.Path;

public class EngineWrapper extends Main {

    public EngineWrapper(Options opts, SonarLintFactory sonarLintFactory, ReportFactory reportFactory, InputFileFinder fileFinder, Path projectHome) {
        super(opts, sonarLintFactory, reportFactory, fileFinder, projectHome);
    }

    /**
     * Required to change method modifier into "public", so we can call it from `cc.App`
     */
    @Override
    public int run() {
        return super.run();
    }
}