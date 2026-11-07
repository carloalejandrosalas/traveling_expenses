package main.java.screens.projects;

import main.java.AppContext;
import main.java.daos.ProjectDao;
import main.java.dtos.projects.Project;
import main.java.screens.AuthenticatedScreen;
import main.java.screens.HomeScreen;
import main.java.utils.*;
import main.java.utils.TablePrinter.Align;

import java.util.ArrayList;

public class ProjectsTableScreen extends AuthenticatedScreen {
    private final ProjectDao projectDao;
    private int page = 1;
    private final int pageSize = 10;
    private Paginate paginate;

    public ProjectsTableScreen(AppContext context) {
        super(context);
        this.projectDao = new ProjectDao(context.getConnection());
    }

    @Override
    public void render() {
        renderProjectsTable();
        renderMenu();
    }

    public void renderProjectsTable() {
        int count = projectDao.getProjectsCount();
        if (count == 0) {
            ScreenUtils.printUI("No projects found.");
            return;
        }

        this.paginate = new Paginate(page, pageSize, count);

        ArrayList<Project> projects = projectDao.getProjects();
        if (projects == null) {
            ScreenUtils.printError("Could not load projects.");
            return;
        }

        ScreenUtils.printHeader("PROJECTS");

        TablePrinter.<Project>create()
                .column("ID", Project::id, Align.RIGHT)
                .column("Owner", Project::ownerName)
                .column("Name", Project::name)
                .column("Start", p -> Mapper.mapLocalDateToLocaleFormat(p.startDate(), "-"))
                .column("End", p -> Mapper.mapLocalDateToLocaleFormat(p.endDate(), "-"))
                .column("Budget", p -> "$%,.2f".formatted(p.budget()), Align.RIGHT)
                .column("Status", p -> Mapper.mapProjectStatusToDisplayName(p.status()))
                .column("Created", p -> Mapper.mapLocalDateTimeToLocaleFormat(p.createdAt(), "-"),
                        30, Align.LEFT)
                .emptyMessage("No projects found.")
                .paginate(paginate)
                .print(projects);
    }

    public void renderMenu() {
        ScreenUtils.printSubtitle("Select an option:");
        ScreenUtils.printMenuItem(1, "Next Page");
        ScreenUtils.printMenuItem(2, "Previous Page");
        ScreenUtils.printMenuItem(3, "Select Project by ID");
        ScreenUtils.printMenuItem(4, "Back to Main Menu");
        Integer selectedOption = ScreenUtils.promptOption(this.context.getScanner(), 4);

        if (selectedOption == null) {
            renderMenu();
            return;
        }

        renderSelectedOption(selectedOption);
    }

    @Override
    public void renderSelectedOption(Integer option) {
        switch (option) {
            case 1 -> {
                if (page >= paginate.getTotalPages()) {
                    ScreenUtils.printWarning("You are already on the last page.");
                } else {
                    page++;
                }
                renderProjectsTable();
                renderMenu();
            }
            case 2 -> {
                if (page > 1) {
                    page--;
                } else {
                    ScreenUtils.printWarning("You are already on the first page.");
                }
                renderProjectsTable();
                renderMenu();
            }
            case 3 -> ProjectUtils.redirectToProjectDetailsScreen(this.context);
            case 4 -> new HomeScreen(this.context).render();
            default -> {
                ScreenUtils.printError("Invalid option. Please try again.");
                renderMenu();
            }
        }
    }
}
